package cn.uparty.memcachedclient.cachemanager;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uparty.memcachedclient.MemcachedClient;

/**
 * 这个类用于实现memcached key快要失效时，主动重新加载新数据的功能。
 * 
 * <pre>
 * 原理是：
 * 在set 到Memcached的value中，加上一个时间，(time, value)，
 * 其中这个time是now + expire，即记录的是数据的失效时间，并不是当前时间。
 * 
 * 当get到数据时，检查时间是否快要超时： (now - time) < (expire - expireOffsetSeconds) * 1000，
 * 如果是，则后台启动一个新的线程：
 *     尝试 add __load_{key}，
 *     如果成功，则去加载新的数据，并set到memcached中。
 *    
 * 默认expireOffsetSeconds是5秒，即memcached的数据还有5秒就要失效时，更新数据到memcached中。
 * 使用例子：
 * 
 * DataLoader dataLoader = new DataLoader() {
 *  //数据加载函数，比如从DB中加载数据
 * 	public <T> T load() {
 * 		try {
 * 			TimeUnit.SECONDS.sleep(1);
 * 		} catch (InterruptedException e) {
 * 		}
 * 		return (T) "xxxx";
 * 	}
 * };
 * 
 * public String tryGet() throws InterruptedException {
 * 	final String testKey = "testKey";
 * 	String value = RefreshCacheManager.tryGet(client, testKey, 20, dataLoader);
 * 	//当返回null时，实际上已经有一个后台线程在加载数据，可以直接返回null给调用者，或者sleep 1秒钟之后再尝试。
 * 	if(value == null) {
 * 		TimeUnit.SECONDS.sleep(1);
 * 		return RefreshCacheManager.tryGet(client, testKey, 20, dataLoader);
 * 	}
 * 	return value;
 * }
 * 
 * public String autoRetryGet() {
 * 	final String testKey = "testKey";
 * 	//如果返回null，会自动重试4次，每次休眠500ms，如果loader能正常工作，重试的概率极小
 * 	String value = RefreshCacheManager.autoRetryGet(client, testKey, 20, dataLoader);
 * 	return value;
 * }
 * 
 * 如果要使用不同的参数设置，请用RefreshConfigBuilder构造一个RefreshConfig，在调用时传递。
*		RefreshConfig refreshConfig = RefreshConfigBuilder.newBuilder()
*				.prefix("__lock_")
*				.retryTimes(20)
*				.retryIntervalsMiliSeconds(100).build();
*		RefreshCacheManager.tryGet(client, refreshConfig, testKey, 20, dataLoader);
 * </pre>
 * 
 * @author zhangli
 * 
 */
public class RefreshCacheManager {
	static Logger logger = LoggerFactory.getLogger(RefreshCacheManager.class);
	static RefreshConfig staticRefreshConfig = new RefreshConfig();

	static public <T> T tryGet(MemcachedClient memcachedClient, final String key, final int expire, final DataLoader dataLoader) {
		return tryGet(memcachedClient, staticRefreshConfig, key, expire, dataLoader);
	}
	
	static public <T> T tryGet(final MemcachedClient memcachedClient, final RefreshConfig refreshConfig, final String key, final int expire, final DataLoader dataLoader) {
		TimeValue<T> timeValue = memcachedClient.get(key);
		final String __load_key = refreshConfig.getPrefix() + key;
		/**
		 * 如果value != null，这时已经拿到数据了，但是还要检查数据是否将要超时。
		 * 如果是将要超时，则要后台启动一个线程去重新加载数据，并刷新到memcached里。
		 * 如果不是将要超时，则直接返回数据。
		 */
		if (timeValue != null) {
			Date now = new Date();
			//判断是否将要超时
			if((timeValue.getDate().getTime() - now.getTime()) < refreshConfig.getExpireOffsetSeconds() * 1000) {
				refreshConfig.getExecutor().execute(new Runnable() {
					@Override
					public void run() {
						boolean add = memcachedClient.add(__load_key, refreshConfig.getLoadKeyExprieSeconds(), 0);
						/**
						 * 如果add __load_{key}成功，说明这个后台线程要去加载数据，并刷新到memcached中。
						 * 如果add __load_{key}不成功，则说明有其它的线程已经去加载数据里，这个线程直接返回即可。
						 */
						if (add) {
							logger.info("value will expire, try to load new data, success add, key:" + __load_key);
							T data = dataLoader.load();
							memcachedClient.set(key, expire, TimeValue.build(data, expire));
							logger.info("DataLoader load data success. key:" + key);
							//是否删掉__load_这个key比较好，防止某些极端情况？
							memcachedClient.delete(__load_key); 
						}
					}
				});
			}
			return timeValue.getValue();
		}else {
			/**
			 * 当 value == null时，说明有可能是第一次加载，则先尝试add __load_{key}，如果成功，
			 * 则调用dataLoader.load()去加载数据，并set到memcached里，否则返回null。
			 */
			boolean add = memcachedClient.add(__load_key, refreshConfig.getLoadKeyExprieSeconds(), 0);
			if(add) {
				T data = null;
				try {
					data = dataLoader.load();
					memcachedClient.set(key, expire, TimeValue.build(data, expire));
					//TODO，是否要删掉 __load_{key}？
				} catch (Throwable t) {
					logger.error("try to load data error!", t);
				}finally {
					if (data != null) {
						return data;
					}
				}
			}
		}
		return null;
	}
	
	static public <T> T autoRetryGet(MemcachedClient memcachedClient, final String key, final int expire, final DataLoader dataLoader) {
		return autoRetryGet(memcachedClient, staticRefreshConfig, key, expire, dataLoader);
	}
	
	static public <T> T autoRetryGet(MemcachedClient memcachedClient, RefreshConfig refreshConfig, final String key, final int expire, final DataLoader dataLoader) {
		int retryTimes = refreshConfig.getRetryTimes();
		while(retryTimes > 0) {
			T t = tryGet(memcachedClient, refreshConfig, key, expire, dataLoader);
			if (t != null) {
				return t;
			}
			retryTimes--;
			try {
				TimeUnit.MILLISECONDS.sleep(refreshConfig.getRetryIntervalsMiliSeconds());
			} catch (InterruptedException e) {
				logger.error("sleep error", e);
			}
		}
		return null;
	}
	
	/**
	 * 用于手动删除 __load_{key}，通常不必调用
	 */
	static public void deleteLoadKey(MemcachedClient memcachedClient, String key) {
		deleteLoadKey(memcachedClient, staticRefreshConfig, key);
	}
	
	/**
	 * 用于手动删除 __load_{key}，通常不必调用
	 */
	static public void deleteLoadKey(MemcachedClient memcachedClient, RefreshConfig refreshConfig, String key) {
		memcachedClient.delete(refreshConfig.getPrefix() + key);
	}
	
	/**
	 * 手动set进{time, value}的数据到memcached里。
	 * 在手动调用DataLoader.load()之后，可以调用这个函数把数据set到memcached中。
	 */
	static public void set(MemcachedClient memcachedClient, String key, int expire, Object value){
		memcachedClient.set(key, expire, TimeValue.build(value, expire));
	}
}

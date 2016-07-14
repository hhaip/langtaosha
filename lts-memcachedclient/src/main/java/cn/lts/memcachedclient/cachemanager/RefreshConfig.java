package cn.lts.memcachedclient.cachemanager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RefreshConfig {
	static Executor staticExecutor = Executors.newCachedThreadPool();
	Executor executor = staticExecutor;

	/**
	 * 用来防止并发load数据的key的前缀
	 */
	String prefix = "__load_";

	/**
	 * __load_{key}，这个key的超时时间
	 */
	int loadKeyExprieSeconds = 2;

	/**
	 * 用来判断数据是否快要超时的时间间隔。 比如从memcached中取回的数据的时间是2014-6-29:12:50:30，
	 * 而当前系统时间是：2014-6-29:12:50:34， 则2014-6-29:12:50:36 - 2014-6-29:12:50:30 = 4
	 * < 5， 说明数据将要超时，则会后台调用DataLoader.load()去加载数据
	 */
	int expireOffsetSeconds = 5;

	/**
	 * autoRetryGet 函数的默认重试次数
	 */
	int retryTimes = 4;
	/**
	 * autoRetryGet 函数的默认重试时间间隔500毫秒
	 */
	int retryIntervalsMiliSeconds = 500;

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getLoadKeyExprieSeconds() {
		return loadKeyExprieSeconds;
	}

	public void setLoadKeyExprieSeconds(int loadKeyExprieSeconds) {
		this.loadKeyExprieSeconds = loadKeyExprieSeconds;
	}

	public int getExpireOffsetSeconds() {
		return expireOffsetSeconds;
	}

	public void setExpireOffsetSeconds(int expireOffsetSeconds) {
		this.expireOffsetSeconds = expireOffsetSeconds;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getRetryIntervalsMiliSeconds() {
		return retryIntervalsMiliSeconds;
	}

	public void setRetryIntervalsMiliSeconds(int retryIntervalsMiliSeconds) {
		this.retryIntervalsMiliSeconds = retryIntervalsMiliSeconds;
	}
}

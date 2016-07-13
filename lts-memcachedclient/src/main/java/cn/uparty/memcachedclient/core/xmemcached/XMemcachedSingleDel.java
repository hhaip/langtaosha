package cn.uparty.memcachedclient.core.xmemcached;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.rubyeye.xmemcached.CommandFactory;
import net.rubyeye.xmemcached.KeyProvider;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.command.Command;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.DefaultKeyProvider;
import net.rubyeye.xmemcached.impl.MemcachedConnector;
import net.rubyeye.xmemcached.networking.MemcachedSession;
import net.rubyeye.xmemcached.utils.ByteUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.yanf4j.core.Session;

/**
 * 专门用来删除某个key在每个节点上的数据
 * 
 * @author ZhangLi
 */
public class XMemcachedSingleDel implements MemcachedClientDel {
	private final Logger log = LoggerFactory.getLogger(XMemcachedSingleDel.class);
	private net.rubyeye.xmemcached.XMemcachedClient xClient;

	private ExecutorService executor;

	public XMemcachedSingleDel(XMemcachedClient xMemcachedClient, int corePoolSize) {
		this.executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 10, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(150));
		this.xClient = xMemcachedClient;
	}

	/**
	 * FIXME:如果子线程没结束，其他线程就set了，会丢失修改,不过在不同应用中由于网络延时也可能出现这种情况
	 */
	@Override
	public void delete(String key) {
		MemcachedConnector connector = (MemcachedConnector) this.xClient.getConnector();
		Collection<InetSocketAddress> serverAddresses = connector.getServerAddresses();
		for (InetSocketAddress serverAddress : serverAddresses) {
			try {
				Session session = connector.getSessionByAddress(serverAddress).element();
				// 尽早失败
				if (!isSessionAvailable(session)) {
					continue;
				}
				executor.execute(new WorkerThread(key, this.xClient.isSanitizeKeys(), this.xClient.getCommandFactory(), (MemcachedSession) session));
			} catch (Exception e) {
				log.error("{msg:'Cluster delete error!',key:'" + key + "',serverAddress:'" + serverAddress + "'}", e);
			}
		}

	}

	private boolean isSessionAvailable(Session session) {
		boolean available = true;
		if (session.isClosed()) {
			log.error("{msg:'Session(" + session.getRemoteSocketAddress().toString() + ") has been closed'}");
			available = false;
		}
		if (((MemcachedSession) session).isExpired()) {
			log.error("msg:'Session expired " + session.getRemoteSocketAddress().toString() + "'}");
			available = false;
		}
		if (((MemcachedSession) session).isAuthFailed()) {
			log.error("msg:'Auth failed to connection " + session.getRemoteSocketAddress().toString() + "'}");
			available = false;
		}
		return available;
	}

	class WorkerThread implements Runnable {

		private String delKey;

		private final boolean sanitizeKeys;

		private final CommandFactory commandFactory;

		private final MemcachedSession session;

		private KeyProvider keyProvider = DefaultKeyProvider.INSTANCE;

		private final Logger logger = LoggerFactory.getLogger(WorkerThread.class);

		public WorkerThread(String key, boolean sanitizeKeys, CommandFactory commandFactory, MemcachedSession memcachedSession) {
			super();
			this.delKey = key;
			this.sanitizeKeys = sanitizeKeys;
			this.commandFactory = commandFactory;
			this.session = memcachedSession;
		}

		@Override
		public void run() {
			String oldThreadName = Thread.currentThread().getName();
			try {
				Thread.currentThread().setName("memcached_del_" + session.getRemoteSocketAddress().toString());
				delete0();
			} catch (MemcachedException e) {
				logger.error("delete0 error:", e);
			} finally {
				Thread.currentThread().setName(oldThreadName);
			}
		}

		private boolean delete0() throws MemcachedException {

			delKey = this.preProcessKey();
			final byte[] keyBytes = ByteUtils.getBytes(delKey);
			ByteUtils.checkKey(keyBytes);

			final Command command = commandFactory.createDeleteCommand(delKey, keyBytes, 0, true);
			session.write(command);
			// // 让其尽早处理(但无法保证)，以减少频繁delete+set导致的丢失修改
			// session.flush();
			// 这里不能马上command.getResult()
			// 因为发送是异步的，接受应答也是异步的，所以这里要通过command.getLatch().await(5000l,TimeUnit.MILLISECONDS);去等待结果返回后再getResult
			// 不过这方法外围并不关心返回结果，所以直接返回true，也不用等待他的返回值
			return true;
		}

		private String preProcessKey() throws MemcachedException {
			String tempkey = this.keyProvider.process(delKey);
			try {
				return this.sanitizeKeys ? URLEncoder.encode(tempkey, "UTF-8") : tempkey;
			} catch (UnsupportedEncodingException e) {
				logger.error(tempkey, e);
				return tempkey;
			}

		}

	}

	@Override
	public void shutdown() {
		executor.shutdownNow();
		try {
			xClient.shutdown();
		} catch (IOException e) {
		}
	}

}

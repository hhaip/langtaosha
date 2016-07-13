package cn.uparty.memcachedclient;

import cn.uparty.memcachedclient.core.xmemcached.XMemcachedSingleDel;
import cn.uparty.memcachedclient.serializer.DefaultTranscoder;
import net.rubyeye.xmemcached.CommandFactory;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.MemcachedSessionLocator;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.buffer.BufferAllocator;
import net.rubyeye.xmemcached.buffer.SimpleBufferAllocator;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uparty.memcachedclient.core.xmemcached.XMemcachedSingleInstanceClient;
import com.google.code.yanf4j.config.Configuration;

/**
 * 用户配置这个类在spring里，建立以xmemcached为底层"使用者应用单机下正确"的aicai.memcacheclient
 * 
 */
@SuppressWarnings("deprecation")
public class XMemcachedFactoryBean {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private XMemcachedSingleInstanceClient cachingClient;

	private String servers;
	private long timeout = 2000L;
	private Transcoder<?> transcoder = new DefaultTranscoder();
	private CommandFactory commandFactory = new BinaryCommandFactory();
	private BufferAllocator bufferAllocator = new SimpleBufferAllocator();
	private Configuration configuration = XMemcachedClientBuilder.getDefaultConfiguration();

	private MemcachedSessionLocator sessionLocator = new KetamaMemcachedSessionLocator();

    @Deprecated
	private int connectionPoolSize = 1;
    @Deprecated
	private int initDelPoolSize = 1;

	public XMemcachedSingleInstanceClient getClient() {
		return cachingClient;
	}

	public void init() throws Exception {
		if (cachingClient == null) {
			synchronized (XMemcachedFactoryBean.class) {
				if (cachingClient == null) {
					cachingClient = createClient();
				}
			}
		}
	}

	private XMemcachedSingleInstanceClient createClient() throws Exception {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(servers));
		builder.setTranscoder(transcoder);
		builder.setCommandFactory(commandFactory);
		builder.setBufferAllocator(bufferAllocator);
		builder.setConfiguration(configuration);
		//builder.setConnectionPoolSize(connectionPoolSize);
		builder.setSessionLocator(sessionLocator);
		// 对于重新愈合的session，把其重新加到可用队列中
		builder.setEnableHealSession(true);
		// 10s连接
		builder.setConnectTimeout(10000L);
		// 这里不使用失败模式，在节点请求失败后，可以迅速把此节点剔除，从而使用另外的节点
		builder.setFailureMode(false);
		net.rubyeye.xmemcached.MemcachedClient xClient = builder.build();
		xClient.setOpTimeout(timeout);

		/*
		 * 用于清除所有节点上的key数据的客户端是需要有失败模式的 情景如下： A B C 三台应用共享
		 * key:mykey，并且key都是被路由到node1的memcached服务器上
		 * 这时候如果A服务器访问node1失败，由于使用的是FailureMode=false模式，那么A服务器上将会把node1删除,
		 * 以后A服务器上，对于mykey请求都是路由到node2中 但是这时BC服务器访问node1是没问题的，从而导致A
		 * 和BC相对于mykey所路由到的memcached服务器不一样。 由于aicai3.0模型中，普遍使用upate立即delete
		 * key的方式保证数据一致 那么如果A服务器上db update了，应该同时删除在node2和node1上的mykey
		 * 单此时A服务器上的client里面是已经没有node1的路由地址了，就无法删除node1上的数据
		 * 所以这里需要设置一个failureMode=true的实例，用来专门负责delete集群上所有节点的key
		 * 把FailureMode设置成true
		 * ，那么在node连接不上的时候，也不会把其剔除，所以这个client实例上永远会有所有node的路由地址。
		 */
		builder.setFailureMode(true);
		// FIXME:这里使用另外的bufferAllocator，因为不确定它是不是线程安全的
		builder.setBufferAllocator(new SimpleBufferAllocator());
		net.rubyeye.xmemcached.MemcachedClient xClientForDel = builder.build();
		/*
		 * 由于多台服务器调用节点不一样的情况只是偶尔发生，所以delete全部节点的情况也只是偶尔发生，
		 * 平时这里没有实际作用，所有这个时间可以设置短点 一旦发生，为了不让子线程大量堆积，这里设置短点也是有道理的
		 */
		xClientForDel.setOpTimeout(100l);
		return new XMemcachedSingleInstanceClient(xClient, new XMemcachedSingleDel((XMemcachedClient) xClientForDel, 1));
	}

	public void destroy() {
		log.info("{msg:'closing the memcachedClient!'}");
		cachingClient.shutdown();
	}

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public Transcoder<?> getTranscoder() {
		return transcoder;
	}

	public void setTranscoder(Transcoder<?> transcoder) {
		this.transcoder = transcoder;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public BufferAllocator getBufferAllocator() {
		return bufferAllocator;
	}

	public void setBufferAllocator(BufferAllocator bufferAllocator) {
		this.bufferAllocator = bufferAllocator;
	}

	public MemcachedSessionLocator getSessionLocator() {
		return sessionLocator;
	}

	public void setSessionLocator(MemcachedSessionLocator sessionLocator) {
		this.sessionLocator = sessionLocator;
	}

    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public int getInitDelPoolSize() {
        return initDelPoolSize;
    }

    public void setInitDelPoolSize(int initDelPoolSize) {
        this.initDelPoolSize = initDelPoolSize;
    }
}

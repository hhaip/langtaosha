package cn.lts.memcachedclient.core.xmemcached;

/**
 * 
 * 用户用这个接口del所有node上的key
 * 
 */
public interface MemcachedClientDel {

	public void delete(String key);

	public void shutdown();
}

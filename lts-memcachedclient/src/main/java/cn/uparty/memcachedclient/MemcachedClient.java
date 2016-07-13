package cn.uparty.memcachedclient;

import java.io.InvalidObjectException;

/**
 * 用户用这个接口get/set/cas/getCas/getNullable/setNullable
 * 
 */
public interface MemcachedClient {
	/**
	 * 
	 * @param key
	 * @param timeout
	 * @return
	 * @throws InvalidObjectException
	 *             和服务器工作状态无关的异常，都以RuntimeException包住的InvalidObjectException方式(
	 *             包括常见的InterruptedException)返回，即标识这个服务器/网络还是可以信任的
	 *             和服务器/网络工作状态相关的异常都，都以RuntimeException包住的其他异常返回，
	 *             包括TimeoutException，即这方面异常达到一定次数和频率，应该排除这服务器IP
	 */
	public <T> T get(final String key, final long timeoutInMs);

	/**
	 * 让使用者信任我们的速度，不需考虑timeout和处理TimeoutException
	 * 
	 * @param key
	 * @return 没命中cache返回null
	 *         反序列化异常和常见的InterruptedException返回null，让应用重新put进新序列化技术的cache
	 *         发生网络异常，以RuntimeException包住的TimeoutException或其他Exception返回
	 */
	public <T> T get(final String key);

	/**
	 * 
	 * @param key
	 * @param expireTime
	 * @return
	 * 
	 *         反序列化异常和常见的InterruptedException返回null，让应用重新put进新序列化技术的cache
	 *         发生网络异常，以RuntimeException包住的TimeoutException或其他Exception返回
	 */
	public <T> T getAndTouch(final String key, final int expireTimeInSecond);

	/**
	 * 
	 * @param key
	 * @param expireTime
	 * @param timeout
	 * @return 和服务器工作状态无关的异常，都以RuntimeException包住的InvalidObjectException方式(
	 *         包括常见的InterruptedException)返回，即标识这个服务器/网络还是可以信任的
	 *         和服务器/网络工作状态相关的异常都，都以RuntimeException包住的其他异常返回，
	 *         包括TimeoutException，即这方面异常达到一定次数和频率，应该排除这服务器IP
	 */
	public <T> T getAndTouch(final String key, final int expireTimeInSecond, final long timeoutInMs);

	/**
	 * 
	 * @param key
	 * @param expireTime
	 * @param value
	 * @return 和服务器工作状态无关的异常，都以RuntimeException包住的InvalidObjectException方式(
	 *         包括常见的InterruptedException)返回，即标识这个服务器/网络还是可以信任的
	 *         和服务器/网络工作状态相关的异常都，都以RuntimeException包住的其他异常返回，
	 */
	public <T> boolean set(final String key, final int expireTimeInSecond, final T value);

	public <T> GetCasResult<T> getCas(final String key);

	public <T> GetCasResult<T> getCas(final String key, final long timeoutInMs);

	public <T> boolean cas(final String key, T value, long version);

	public <T> boolean cas(final String key, final int expireTimeInSecond, T value, long version);

	public <T> boolean add(final String key, final int expireTimeInSecond, final Object value);

	/**
	 * 不会抛任何异常，如果有错误，内部会自己log，自己报警
	 * 
	 * @param key
	 */
	public void delete(final String key);

    /**
     * memcache计数器，原子递增
     * @param key
     * @param value 递增步长
     * @param initValue 如果不存在时的初始值
     * return 返回新增后的结果数据
     */
    public long increment(final String key, final long value, final long initValue);

    /**
     * memcache计数器，原子递增
     * @param key
     * @param value  递增步长
     * @param initValue  如果不存在时的初始值
     * @param exp  key过期时间
     * @return
     */
    public long increment(final String key, final long value, final long initValue, int exp);
    
    /**
     * memcache计数器，原子递减
     * @param key
     * @param value  递减步长
     * @param initValue 如果不存在时的初始值
     * return 返回递减后的结果
     */
    public long decrement(final String key, final long value, final long initValue);
}

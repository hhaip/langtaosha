package cn.lts.memcachedclient.serializer;

/**
 * 序列化工具接口
 * @author zhangli
 */
public interface Serializer {
	public byte[] serialize(Object object);

	public Object deserialize(byte[] data);
}

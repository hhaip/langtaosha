package cn.lts.memcachedclient.serializer;

import cn.lts.common.exception.InternalRuntimeException;
import net.rubyeye.xmemcached.transcoders.CachedData;
import net.rubyeye.xmemcached.transcoders.CompressionMode;
import net.rubyeye.xmemcached.transcoders.Transcoder;


/**
 * 默认的编解码器
 *
 * @author zhangli
 */
public class DefaultTranscoder implements Transcoder<Object> {
	private int maxSize;
	/**
	 * 序列化类型，默认是hessian，可通过此属性切换序列化方式，暂时只支持默认版本。 以后可通过注入此属性切换版本。
	 * 
	 * @see SerializerTypeConstant
	 */
	private int serializerType = SerializerTypeConstant.HESSIAN_DEFAULT_CLASSLOADER;

	public DefaultTranscoder() {
		this(CachedData.MAX_SIZE);
	}

	public DefaultTranscoder(int max) {
		this.maxSize = max;
	}

	@Override
	public CachedData encode(Object object) {
		Serializer serializer = SerializerFactory.findSerializer();
		byte[] b = null;
		try {
			b = serializer.serialize(object);
		} catch (Exception e) {
			if (!(e instanceof InternalRuntimeException)) {
				throw new RuntimeException(e);
			}
		}
		return new CachedData(serializerType, b, this.maxSize, -1);
	}

	@Override
	public Object decode(CachedData cachedData) {
		// 这里得到的flag不一定是你本应用的序列化方式，
		// 因为同一个key可能其他应用使用了别的序列化方式，所以要根据具体的flag选择反序列化工具
        // 不再支持kyro序列化
		int flag = cachedData.getFlag();
        if(flag != SerializerTypeConstant.HESSIAN_DEFAULT_CLASSLOADER) {
            return null;
        }
		Serializer serializer = SerializerFactory.findSerializer();
		Object object = null;
		try {
			object = serializer.deserialize(cachedData.getData());
		} catch (Exception e) {
			if (!(e instanceof InternalRuntimeException)) {
				throw new RuntimeException(e);
			}
		}
		return object;
	}

	@Override
	public void setPrimitiveAsString(boolean primitiveAsString) {

	}

	@Override
	public void setPackZeros(boolean packZeros) {

	}

	@Override
	public boolean isPrimitiveAsString() {
		// 不把基本类型标识为String的话，xmemcached会在set/get的时候分布增加/删除前面4位标识数据类型
		return false;
	}

	@Override
	public boolean isPackZeros() {
		// 这个地方不会用到
		return false;
	}

	@Override
	public void setCompressionThreshold(int to) {
		// 没必要对数据进行压缩，只要“纯”的序列化即可
	}

	@Override
	public void setCompressionMode(CompressionMode compressMode) {
		// 没必要对数据进行压缩，只要“纯”的序列化即可
	}

	public void setSerializerType(int serializerType) {
		this.serializerType = serializerType;
	}

}

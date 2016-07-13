package cn.uparty.memcachedclient.serializer;

import cn.uparty.memcachedclient.serializer.hessian.HessianSerializer;

/**
 * 序列化工厂
 * @author zhangli
 */
public final class SerializerFactory {

	/*private static Map<Integer, Serializer> serializerMap = new HashMap<Integer, Serializer>();
	static {
		serializerMap.put(SerializerTypeConstant.HESSIAN_DEFAULT_CLASSLOADER, new HessianSerializer());
        serializerMap.put(SerializerTypeConstant.KRYO_DEFAULT_CLASSLOADER, new KryoSerializer());
    }

    public static Serializer findSerializer(Integer flag) {
		return serializerMap.get(flag);
	}
    */

    private final static Serializer serializer = new HessianSerializer();

    public static Serializer findSerializer() {
        return serializer;
    }
}

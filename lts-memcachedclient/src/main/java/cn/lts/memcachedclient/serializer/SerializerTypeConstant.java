package cn.lts.memcachedclient.serializer;

/**
 * 序列化类型常量，放置于CacheData.flag中，CacheData的高位或许日后还有其他用途，
 * 命名规则：前缀为序列化技术，后缀为此序列化技术的版本号，后缀名为DEFAULT_CLASSLOADER表示
 * 采用此 webapp 默认classloader的序列化技术jar 
 *
 * @author zhangli
 *
 */
public class SerializerTypeConstant {
	/**
	 * 当前版本为3.2.1
	 */
	public static final int HESSIAN_DEFAULT_CLASSLOADER = 1;
	
	/**
	 * 当前版本为1.03
	 */
    @Deprecated /*不在提供kero支持，只支持hessian serialize*/
	public static final int KRYO_DEFAULT_CLASSLOADER = 2;
}

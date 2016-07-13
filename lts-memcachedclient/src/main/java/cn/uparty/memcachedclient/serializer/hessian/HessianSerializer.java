package cn.uparty.memcachedclient.serializer.hessian;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.uparty.appmodel.exception.InternalRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uparty.memcachedclient.serializer.Serializer;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

/**
 * hessian的序列化工具
 * @author ZhangLi
 */
public class HessianSerializer implements Serializer {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	private static final SerializerFactory serializerFactory = new SerializerFactory();
	@Override
	public byte[] serialize(Object object) {
		byte[] b = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Hessian2Output out = new Hessian2Output(bos);
		out.setSerializerFactory(serializerFactory);
		try {
			out.writeObject(object);
			out.flush();
			b = bos.toByteArray();
		} catch (IOException e) {
			logger.error("{msg:'hessian serialize error!'}", e);
			throw new InternalRuntimeException("{msg:'hessian serialize error!'}", e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
			try {
				bos.close();
			} catch (IOException e) {
			}
		}
		return b;
	}

	@Override
	public Object deserialize(byte[] data) {
		if(data == null) {
			return null;
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		Hessian2Input in = new Hessian2Input(bis);
		in.setSerializerFactory(serializerFactory);
		Object object = null;
		try {
			object = in.readObject();
		} catch (IOException e) {
			logger.error("{msg:'hessian unserialize error!'}", e);
			throw new InternalRuntimeException("{msg:'hessian unserialize error!'}", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
			try {
				bis.close();
			} catch (IOException e) {
			}
		}
		return object;
	}

}

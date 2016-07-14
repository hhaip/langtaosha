package com.caucho.hessian.io.java8;

import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.AbstractSerializer;

import java.io.IOException;

/**
 * author: Zhang Li
 * created on 15-3-25.
 */
public class Java8TimeSerializer<T extends Java8TimeHandle> extends AbstractSerializer {

    //handle 具体类型
    private Class<T> handleType;

    private Java8TimeSerializer(Class<T> handleType) {
        this.handleType = handleType;
    }

    public static <T extends Java8TimeHandle> Java8TimeSerializer create(Class<T> handleType) {
        return new Java8TimeSerializer(handleType);
    }

    @Override
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        if(obj == null) {
            out.writeNull();
            return;
        }
        T handle = null;
        try {
            handle = handleType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("the class :" + handleType.getName() + " init failed:" + e.getMessage(), e);
        }
        handle.init(obj);
        out.writeObject(handle);
    }
}

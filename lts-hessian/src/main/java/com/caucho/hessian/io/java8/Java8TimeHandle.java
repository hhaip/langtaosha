package com.caucho.hessian.io.java8;

import com.caucho.hessian.io.HessianHandle;

import java.io.Serializable;

/**
 * author: Zhang Li
 * created on 15-3-25.
 */
public abstract class Java8TimeHandle<T> implements HessianHandle, Serializable {

    private static final long serialVersionUID = -8160221934616339966L;

    /**
     * @param time
     */
    public abstract void init(T time);
}

package com.caucho.hessian.io.java8;

import java.time.Instant;

/**
 * author: Zhang Li
 * created on 15-3-24.
 */
public class InstantHandle extends Java8TimeHandle<Instant> {
    private static final long serialVersionUID = -4367309317780077156L;

    private long seconds;

    private int nanos;

    public InstantHandle() {
    }

    public InstantHandle(Instant instant) {
        init(instant);
    }

    @Override
    public void init(Instant instant) {
        if(instant == null) {
            return;
        }
        this.seconds = instant.getEpochSecond();
        this.nanos = instant.getNano();
    }

    private Object readResolve() {
        return Instant.ofEpochSecond(seconds, nanos);
    }
}

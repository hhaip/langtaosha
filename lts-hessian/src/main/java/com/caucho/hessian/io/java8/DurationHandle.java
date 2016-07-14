package com.caucho.hessian.io.java8;

import java.time.Duration;

/**
 * author: Zhang Li
 * created on 15-3-25.
 */
public class DurationHandle extends Java8TimeHandle<Duration> {
    private static final long serialVersionUID = -4367309317780077156L;

    private long seconds;

    private int nanos;

    public DurationHandle() {
    }

    public DurationHandle(Duration duration) {
        init(duration);
    }

    @Override
    public void init(Duration duration) {
        if(duration == null) {
            return;
        }
        this.seconds = duration.getSeconds();
        this.nanos = duration.getNano();
    }

    private Duration readResolve() {
        return Duration.ofSeconds(seconds, nanos);
    }
}

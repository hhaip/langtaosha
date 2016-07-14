package com.caucho.hessian.io.java8;

import java.time.ZoneOffset;

/**
 * author: Zhang Li
 * created on 15-3-24.
 */
public class ZoneOffsetHandle extends Java8TimeHandle<ZoneOffset> {
    private static final long serialVersionUID = 8841589723587858789L;

    private int seconds;

    public ZoneOffsetHandle() {
    }

    public ZoneOffsetHandle(ZoneOffset zoneOffset) {
        init(zoneOffset);
    }

    @Override
    public void init(ZoneOffset zoneOffset) {
        if(zoneOffset == null) {
            return;
        }
        this.seconds = zoneOffset.getTotalSeconds();
    }

    private ZoneOffset readResolve() {
        return ZoneOffset.ofTotalSeconds(seconds);
    }
}

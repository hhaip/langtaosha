package com.caucho.hessian.io.java8;

import java.time.ZoneId;

/**
 * author: Zhang Li
 * created on 15-3-25.
 */
public class ZoneIdHandle extends Java8TimeHandle<ZoneId> {
    private static final long serialVersionUID = 8789182864066905552L;

    private String zoneId;

    public ZoneIdHandle() {
    }

    public ZoneIdHandle(ZoneId zoneId) {
        init(zoneId);
    }

    @Override
    public void init(ZoneId zoneId) {
        if(zoneId == null) {
            return;
        }
        this.zoneId = zoneId.getId();
    }

    private ZoneId readResolve() {
        return ZoneId.of(zoneId);
    }
}

package com.caucho.hessian.io.java8;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

/**
 * author: Zhang Li
 * created on 15-3-24.
 */
public class OffsetTimeHandle extends Java8TimeHandle<OffsetTime> {
    private static final long serialVersionUID = -3269846941421652860L;

    private LocalTime localTime;

    private ZoneOffset zoneOffset;

    public OffsetTimeHandle() {
    }

    public OffsetTimeHandle(OffsetTime offsetTime) {
        init(offsetTime);
    }

    @Override
    public void init(OffsetTime offsetTime) {
        if( offsetTime != null) {
            this.zoneOffset = offsetTime.getOffset();
            this.localTime = offsetTime.toLocalTime();
        }
    }

    private OffsetTime readResolve() {
        return OffsetTime.of(localTime, zoneOffset);
    }
}

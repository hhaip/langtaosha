package com.caucho.hessian.io.java8;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * author: Zhang Li
 * created on 15-3-25.
 */
public class ZonedDateTimeHandle extends Java8TimeHandle<ZonedDateTime> {
    /**
     * The local date-time.
     */
    private LocalDateTime dateTime;
    /**
     * The offset from UTC/Greenwich.
     */
    private ZoneOffset offset;
    /**
     * The time-zoneId.
     */
    private String zoneId;


    public ZonedDateTimeHandle() {
    }

    public ZonedDateTimeHandle(ZonedDateTime zonedDateTime) {
        init(zonedDateTime);
    }

    @Override
    public void init(ZonedDateTime zonedDateTime) {
        if(zonedDateTime == null) {
            return;
        }
        this.dateTime = zonedDateTime.toLocalDateTime();
        this.offset = zonedDateTime.getOffset();
        if(zonedDateTime.getZone() != null) {
            this.zoneId = zonedDateTime.getZone().getId();
        }
    }

    private ZonedDateTime readResolve() {
        return ZonedDateTime.ofLocal(dateTime, ZoneId.of(zoneId), offset);
    }
}

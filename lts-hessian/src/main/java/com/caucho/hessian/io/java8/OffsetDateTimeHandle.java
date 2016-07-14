package com.caucho.hessian.io.java8;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * author: Zhang Li
 * created on 15-3-25.
 */
public class OffsetDateTimeHandle extends Java8TimeHandle<OffsetDateTime> {
    private static final long serialVersionUID = -7823900532640515312L;

    /**
     * The local date-time.
     */
    private LocalDateTime dateTime;
    /**
     * The offset from UTC/Greenwich.
     */
    private ZoneOffset offset;

    public OffsetDateTimeHandle() {
    }

    public OffsetDateTimeHandle(OffsetDateTime offsetDateTime) {
        init(offsetDateTime);
    }

    @Override
    public void init(OffsetDateTime time) {
        if (time == null) {
            return;
        }
        this.dateTime = time.toLocalDateTime();
        this.offset = time.getOffset();
    }

    private OffsetDateTime readResolve() {
        return OffsetDateTime.of(dateTime, offset);
    }
}

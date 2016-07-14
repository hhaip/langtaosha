package com.caucho.hessian.io.java8;

import com.caucho.hessian.io.HessianHandle;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * author: Zhang Li
 * created on 15-3-25.
 */
public class LocalDateTimeHandle extends Java8TimeHandle<LocalDateTime> {
    private static final long serialVersionUID = 7563825215275989361L;

    /**
     * The date part.
     */
    private LocalDate date;
    /**
     * The time part.
     */
    private LocalTime time;

    public LocalDateTimeHandle() {
    }

    public LocalDateTimeHandle(LocalDateTime localDateTime) {
        init(localDateTime);
    }

    @Override
    public void init(LocalDateTime localDateTime) {
        if(localDateTime == null) {
            return;
        }
        this.date = localDateTime.toLocalDate();
        this.time = localDateTime.toLocalTime();
    }

    private LocalDateTime readResolve() {
        return LocalDateTime.of(date, time);
    }
}

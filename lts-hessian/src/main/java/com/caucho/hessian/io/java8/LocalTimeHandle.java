package com.caucho.hessian.io.java8;


import java.time.LocalTime;

/**
 * author: Zhang Li
 * created on 15-3-24.
 */
public class LocalTimeHandle extends Java8TimeHandle<LocalTime> {
    private static final long serialVersionUID = -5892919085390462315L;

    /**
     * The hour.
     */
    private int hour;
    /**
     * The minute.
     */
    private int minute;
    /**
     * The second.
     */
    private int second;
    /**
     * The nanosecond.
     */
    private int nano;

    public LocalTimeHandle() {
    }

    public LocalTimeHandle(LocalTime localTime) {
        init(localTime);
    }

    @Override
    public void init(LocalTime localTime) {
        if(localTime == null) {
            return;
        }
        this.hour = localTime.getHour();
        this.minute = localTime.getMinute();
        this.second = localTime.getSecond();
        this.nano = localTime.getNano();

    }

    private LocalTime readResolve() {
        return LocalTime.of(hour, minute, second, nano);
    }
}

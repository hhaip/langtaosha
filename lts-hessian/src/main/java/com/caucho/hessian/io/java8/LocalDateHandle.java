package com.caucho.hessian.io.java8;

import java.time.LocalDate;

/**
 * author: Zhang Li
 * created on 15-3-24.
 */
public class LocalDateHandle extends Java8TimeHandle<LocalDate> {
    private static final long serialVersionUID = 166018689500019951L;

    /**
     * The year.
     */
    private int year;
    /**
     * The month-of-year.
     */
    private short month;
    /**
     * The day-of-month.
     */
    private short day;

    public LocalDateHandle() {
    }

    public LocalDateHandle(LocalDate localDate) {
        init(localDate);
    }

    @Override
    public void init(LocalDate localDate) {
        if(localDate == null) {
        }
        this.year = localDate.getYear();
        this.month = (short) localDate.getMonthValue();
        this.day = (short) localDate.getDayOfMonth();
    }

    public LocalDate readResolve() {
        return LocalDate.of(year, month, day);
    }
}

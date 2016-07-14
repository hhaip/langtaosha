package com.caucho.hessian.io.java8;

import java.time.MonthDay;

/**
 * author: Zhang Li
 * created on 15-3-25.
 */
public class MonthDayHandle extends Java8TimeHandle<MonthDay> {
    /**
     * The month-of-year, not null.
     */
    private int month;
    /**
     * The day-of-month.
     */
    private int day;


    public MonthDayHandle() {
    }

    public MonthDayHandle(MonthDay monthDay) {
        init(monthDay);
    }

    @Override
    public void init(MonthDay monthDay) {
        if(monthDay == null) {
            return;
        }
        this.month = monthDay.getMonthValue();
        this.day = monthDay.getDayOfMonth();
    }

    private MonthDay readResolve() {
        return MonthDay.of(month, day);
    }
}

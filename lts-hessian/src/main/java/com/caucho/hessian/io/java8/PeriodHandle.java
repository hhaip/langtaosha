package com.caucho.hessian.io.java8;

import java.time.Period;

/**
 * author: Zhang Li
 * created on 15-3-25.
 */
public class PeriodHandle extends Java8TimeHandle<Period> {
    private static final long serialVersionUID = 4399720381283781186L;
    /**
     * The number of years.
     */
    private int years;
    /**
     * The number of months.
     */
    private int months;
    /**
     * The number of days.
     */
    private int days;


    public PeriodHandle() {
    }

    public PeriodHandle(Period period) {
        init(period);
    }

    @Override
    public void init(Period period) {
        if(period == null) {
           return;
        }
        this.years = period.getYears();
        this.months = period.getMonths();
        this.days = period.getDays();
    }

    private Period readResolve() {
        return Period.of(years, months, days);
    }
}

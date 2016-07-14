package com.caucho.hessian.io.java8;

import java.time.YearMonth;

/**
 * author: Zhang Li
 * created on 15-3-25.
 */
public class YearMonthHandle extends Java8TimeHandle<YearMonth> {
    private static final long serialVersionUID = -4150786187896925314L;

    /**
     * The year.
     */
    private int year;
    /**
     * The month-of-year, not null.
     */
    private int month;


    public YearMonthHandle() {
    }

    public YearMonthHandle(YearMonth yearMonth) {
        init(yearMonth);
    }

    @Override
    public void init(YearMonth yearMonth) {
        if(yearMonth == null) {
            return;
        }
        this.year = yearMonth.getYear();
        this.month = yearMonth.getMonthValue();
    }

    private YearMonth readResolve() {
        return YearMonth.of(year, month);
    }
}

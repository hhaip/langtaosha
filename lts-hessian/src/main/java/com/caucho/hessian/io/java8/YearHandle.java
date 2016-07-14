package com.caucho.hessian.io.java8;

import java.time.Year;
import java.time.temporal.ChronoField;

/**
 * author: Zhang Li
 * created on 15-3-25.
 */
public class YearHandle extends Java8TimeHandle<Year> {
    private static final long serialVersionUID = -6299552890287487926L;

    private int year;

    public YearHandle() {
    }

    public YearHandle(Year year) {
        init(year);
    }

    @Override
    public void init(Year time) {
        if(time == null) {
            return;
        }
        this.year = time.getValue();
    }

    private Year readResolve() {
        ChronoField.YEAR.checkValidValue(year);
        return Year.of(year);
    }
}

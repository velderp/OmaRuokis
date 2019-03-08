package com.example.omaruokis.utilities;

import java.util.Calendar;
import java.util.Date;

public class DateHolder {
    private Date date;

    private static final DateHolder ourInstance = new DateHolder();

    public static DateHolder getInstance() {
        return ourInstance;
    }

    private DateHolder() {
        this.date = Calendar.getInstance().getTime();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void resetDate() {
        this.date = Calendar.getInstance().getTime();
    }

    public String dateToString () {
        InputChecker checker = new InputChecker();
        return checker.formatDate(this.date);
    }
}

package com.example.omaruokis.utilities;

import java.util.Calendar;
import java.util.Date;

public class DateHolder extends Formatter {
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
        return formatDateToString(this.date);
    }

    public String currentDateToString () {
        return formatDateToString(Calendar.getInstance().getTime());
    }
}

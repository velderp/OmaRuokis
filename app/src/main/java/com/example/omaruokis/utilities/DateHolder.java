package com.example.omaruokis.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void resetDate() {
        this.date = Calendar.getInstance().getTime();
    }

    public String dateToString () {
        DateFormat df = new SimpleDateFormat(InputChecker.DATE_FORMAT);
        return df.format(this.date);
    }
}

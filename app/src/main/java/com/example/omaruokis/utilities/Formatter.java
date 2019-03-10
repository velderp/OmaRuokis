package com.example.omaruokis.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Formatter {

    private static final String DATE_FORMAT = "d.M.yyyy";
    static final DateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

    String formatDateToString(String input) {
        Date date;
        try {
            date = SIMPLE_DATE_FORMAT.parse(input);
        } catch (ParseException e) {
            return "00.00.0000";
        }
        return SIMPLE_DATE_FORMAT.format(date);
    }

    String formatDateToString(Date date) {
        return SIMPLE_DATE_FORMAT.format(date);
    }

    public Calendar dateStringToCalendar(String input) {
        Calendar calendar = Calendar.getInstance();
        Date date;
        try {
            date = SIMPLE_DATE_FORMAT.parse(input);
        } catch (ParseException e) {
            return calendar;
        }
        calendar.setTime(date);
        return calendar;
    }
}

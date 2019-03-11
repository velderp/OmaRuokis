package com.example.omaruokis.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Provides methods for formatting dates to d.M.yyyy format.
 *
 * @author  Veli-Pekka
 */
public class Formatter {

    private static final String DATE_FORMAT = "d.M.yyyy";
    static final DateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

    /**
     * Formats a date string to d.M.yyyy format.
     *
     * @param   input   date string to format
     * @return          formatted string
     */
    String formatDateToString(String input) {
        Date date;
        try {
            date = SIMPLE_DATE_FORMAT.parse(input);
        } catch (ParseException e) {
            return "00.00.0000";
        }
        return SIMPLE_DATE_FORMAT.format(date);
    }

    /**
     * Converts a date (type) to a string in d.M.yyyy format.
     *
     * @param   date    date to format
     * @return          formatted date string
     */
    String formatDateToString(Date date) {
        return SIMPLE_DATE_FORMAT.format(date);
    }

    /**
     * Converts a date string to a calendar.
     *
     * @param   input   date string to convert
     * @return          <code>calendar</code> with given date if date string is valid,
     *                  otherwise <code>calendar</code> with current date.
     */
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

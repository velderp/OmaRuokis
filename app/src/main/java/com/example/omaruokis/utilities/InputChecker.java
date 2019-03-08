package com.example.omaruokis.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InputChecker {
    private static final String DATE_FORMAT = "d.M.yyyy";
    private static final DateFormat df = new SimpleDateFormat(DATE_FORMAT);

    public InputChecker() {
    }

    boolean checkDateValidity(String date, int minYear, int maxYear) {
        df.setLenient(false);
        try {
            df.parse(date);
        } catch (ParseException e) {
            return false;
        }
        String[] parts = date.split("\\.");
        int year = Integer.parseInt(parts[2]);
        return !(year < minYear || year > maxYear);
    }

    boolean dateBeforeCurrent(String input) {
        df.setLenient(false);
        Date date;
        Date current = Calendar.getInstance().getTime();
        try {
            date = df.parse(input);
            current = df.parse(df.format(current));
        } catch (ParseException e) {
            return false;
        }
        return date.before(current);
    }

    String formatDate(String input) {
        Date date;
        try {
            date = df.parse(input);
        } catch (ParseException e) {
            return "00.00.0000";
        }
        return df.format(date);
    }

    String formatDate(Date date) {
        return df.format(date);
    }

    public boolean checkInt(String input, int min, int max) {
        int inputInt;
        try {
            inputInt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return !(inputInt < min || inputInt > max);
    }
}

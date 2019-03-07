package com.example.omaruokis;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class InputChecker {
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    InputChecker() {
    }

    boolean checkDateValidity(String date, int minYear, int maxYear) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
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
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
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

    boolean checkInt(String input, int min, int max) {
        int inputInt;
        try {
            inputInt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return !(inputInt < min || inputInt > max);
    }
}

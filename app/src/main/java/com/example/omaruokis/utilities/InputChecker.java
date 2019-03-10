package com.example.omaruokis.utilities;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class InputChecker extends Formatter {

    public InputChecker() {
    }

    boolean checkDateValidity(String date, int minYear, int maxYear) {
        SIMPLE_DATE_FORMAT.setLenient(false);
        try {
            SIMPLE_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            return false;
        }
        String[] parts = date.split("\\.");
        int year = Integer.parseInt(parts[2]);
        return !(year < minYear || year > maxYear);
    }

    boolean dateBeforeCurrent(String input) {
        SIMPLE_DATE_FORMAT.setLenient(false);
        Date date;
        Date current = Calendar.getInstance().getTime();
        try {
            date = SIMPLE_DATE_FORMAT.parse(input);
            current = SIMPLE_DATE_FORMAT.parse(SIMPLE_DATE_FORMAT.format(current));
        } catch (ParseException e) {
            return false;
        }
        return date.before(current);
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

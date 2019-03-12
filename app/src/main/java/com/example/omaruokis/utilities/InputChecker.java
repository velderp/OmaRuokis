package com.example.omaruokis.utilities;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Provides methods for checking date and number string validity.
 *
 * @author  Veli-Pekka
 */
public class InputChecker extends Formatter {

    /**
     * Class constructor.
     *
     * @author  Veli-Pekka
     */
    public InputChecker() {
    }

    /**
     * Checks whether a string is a valid date string and year is between given years.
     *
     * @param   date    date string to check
     * @param   minYear minimum acceptable year
     * @param   maxYear maximum acceptable year
     * @return          <code>true</code> if string is valid and year is within range,
     *                  <code>false</code> otherwise.
     */
    boolean checkYearValidity(String date, int minYear, int maxYear) {
        SIMPLE_DATE_FORMAT.setLenient(false);
        try {
            SIMPLE_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            return false;
        }
        // extract year from string
        String[] parts = date.split("\\.");
        int year = Integer.parseInt(parts[2]);
        return !(year < minYear || year > maxYear);
    }

    /**
     * Checks whether a string is a valid date string and date is before current date.
     *
     * @param   input   date string to check
     * @return          <code>true</code> if string is a valid date that precedes current date,
     *                  <code>false</code> otherwise.
     */
    boolean dateBeforeCurrent(String input) {
        SIMPLE_DATE_FORMAT.setLenient(false);
        Date date;
        Date current = Calendar.getInstance().getTime();
        try {
            date = SIMPLE_DATE_FORMAT.parse(input);
            // Get rid of time of day to get correct results.
            current = SIMPLE_DATE_FORMAT.parse(SIMPLE_DATE_FORMAT.format(current));
        } catch (ParseException e) {
            return false;
        }
        return date.before(current);
    }

    /**
     * Checks whether a string is a valid integer and between given numbers.
     *
     * @param   input   number string to check
     * @param   min     minimum acceptable number
     * @param   max     maximum acceptable number
     * @return          <code>true</code> if string is a number and within range,
     *                  <code>false</code> otherwise.
     */
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

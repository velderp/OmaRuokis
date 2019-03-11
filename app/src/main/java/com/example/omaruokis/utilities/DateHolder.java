package com.example.omaruokis.utilities;

import java.util.Calendar;
import java.util.Date;

/**
 * DateHolder singleton holds the currently selected date and provides methods
 * for changing, resetting and getting it as a string in d.M.yyyy format.
 *
 * @author  Veli-Pekka
 */
public class DateHolder extends Formatter {
    private Date selectedDate;

    private static final DateHolder ourInstance = new DateHolder();

    /**
     * Returns our instance.
     *
     * @return  this singleton's instance
     */
    public static DateHolder getInstance() {
        return ourInstance;
    }

    private DateHolder() {
        this.selectedDate = Calendar.getInstance().getTime();
    }

    /**
     * Sets the date stored in this singleton instance.
     *
     * @param   selectedDate  selected date
     */
    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    /**
     * Resets the date stored in this singleton instance to current date.
     */
    public void resetDate() {
        this.selectedDate = Calendar.getInstance().getTime();
    }

    /**
     * Returns the stored date as a string.

     * @return  stored date in d.M.yyyy format
     */
    public String selectedDateToString() {
        return formatDateToString(this.selectedDate);
    }

    /**
     * Returns the current date as a string.
     *
     * @return  date in d.M.yyyy format
     */
    public String currentDateToString () {
        return formatDateToString(Calendar.getInstance().getTime());
    }
}

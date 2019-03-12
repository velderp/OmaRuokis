package com.example.omaruokis.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Provides methods for setting and getting saved user details from a
 * <code>SharedPreferences</code> file.
 *
 * @author  Veli-Pekka
 */
public class UserPrefs extends InputChecker {
    // SharedPreferences key strings
    private static final String PREF_USER = "UserInfo";
    private static final String USER_DOB = "DateOfBirth";
    private static final String USER_SEX = "Sex";
    private static final String USER_WEIGHT = "Weight";
    private static final String USER_HEIGHT = "Height";
    private static final String USER_PAL = "DefaultActivityLevel";
    private static final String USER_INFO_FILLED = "InfoFilled";
    // User details min/max values
    private static final int MIN_YEAR = 1903;
    private static final int MAX_YEAR = 2200;
    private static final int MIN_HEIGHT = 24;
    private static final int MAX_HEIGHT = 272;
    public static final int MIN_WEIGHT = 1;
    public static final int MAX_WEIGHT = 635;
    private Context context;

    /**
     * Class constructor.
     *
     * @param   context     creator's <code>context</code>
     */
    public UserPrefs(Context context) {
        this.context = context;
    }

    private SharedPreferences prefGet() {
        return context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor prefEdit() {
        return prefGet().edit();
    }

    /**
     * Returns user's saved date of birth from a <>code</>SharedPreferences<>code</> file.
     *
     * @return  date as a string in d.M.yyyy format
     */
    public String prefGetUserDob() {
        return prefGet().getString(USER_DOB, "");
    }

    /**
     * Checks validity of date of birth and saves it to a
     * <>code</>SharedPreferences<>code</> file if valid.
     *
     * @param   dateOfBirth date as a string in d.M.yyyy format
     * @return              <code>true</code> if date is valid, <code>false</code> otherwise
     * @see     InputChecker#checkYearValidity(String, int, int)
     * @see     InputChecker#dateBeforeCurrent(String)
     */
    public boolean prefSetUserDob(String dateOfBirth) {
        if (checkYearValidity(dateOfBirth, MIN_YEAR, MAX_YEAR)
                && (dateBeforeCurrent(dateOfBirth)
                || dateOfBirth.equals(DateHolder.getInstance().currentDateToString()))) {
            prefEdit().putString(USER_DOB, formatDateToString(dateOfBirth)).apply();
            return true;
        }
        return false;
    }

    /**
     * Returns user's saved sex from a <>code</>SharedPreferences<>code</> file.
     *
     * @return  sex as a string with value "M" or "F"
     */
    public String prefGetUserSex() {
        return prefGet().getString(USER_SEX, "M");
    }

    /**
     * Saves user's sex to a <>code</>SharedPreferences<>code</> file as a string.
     *
     * @param   sex string with value "M" or "F"
     */
    public void prefSetUserSex(String sex) {
        prefEdit().putString(USER_SEX, sex).apply();
    }

    /**
     * Returns user's saved weight from a <>code</>SharedPreferences<>code</> file.
     *
     * @return  weight in kg as an int
     */
    public int prefGetUserWeight() {
        return prefGet().getInt(USER_WEIGHT, -1);
    }

    /**
     * Checks validity of weight and saves it to a
     * <>code</>SharedPreferences<>code</> file as an int if valid.
     *
     * @param   weight  weight in kg as a string
     * @return          <code>true</code> if weigh is valid, <code>false</code> otherwise
     * @see             InputChecker#checkInt(String, int, int)
     */
    public boolean prefSetUserWeight(String weight) {
        if (checkInt(weight, MIN_WEIGHT, MAX_WEIGHT)) {
            prefEdit().putInt(USER_WEIGHT, Integer.parseInt(weight)).apply();
            return true;
        }
        return false;
    }

    /**
     * Returns user's saved height from a <>code</>SharedPreferences<>code</> file.
     *
     * @return  height in cm as an int
     */
    public int prefGetUserHeight() {
        return prefGet().getInt(USER_HEIGHT, -1);
    }

    /**
     * Checks validity of height and saves it to a
     * <>code</>SharedPreferences<>code</> file as an int if valid.
     *
     * @param   height  height in cm as a string
     * @return          <code>true</code> if height is valid, <code>false</code> otherwise
     * @see             InputChecker#checkInt(String, int, int)
     */
    public boolean prefSetUserHeight(String height) {
        if (checkInt(height, MIN_HEIGHT, MAX_HEIGHT)) {
            prefEdit().putInt(USER_HEIGHT, Integer.parseInt(height)).apply();
            return true;
        }
        return false;
    }

    /**
     * Returns user's saved physical activity level from a
     * <>code</>SharedPreferences<>code</> file.
     *
     * @return  physical activity level as an int.
     */
    public int prefGetUserPal() {
        return prefGet().getInt(USER_PAL, 0);
    }

    /**
     * Saves user's physical activity level to a <>code</>SharedPreferences<>code</>
     * file as an int.
     *
     * @param   activityLevel   activity level as a spinner index
     */
    public void prefSetUserPal(int activityLevel) {
        prefEdit().putInt(USER_PAL, activityLevel).apply();
    }

    /**
     * Returns whether user's full details are saved to a
     * <>code</>SharedPreferences<>code</> file.
     *
     * @return  <code>true</code> if user's details found, <code>false</code> otherwise
     */
    public boolean prefGetInfoFilled() {
        return prefGet().getBoolean(USER_INFO_FILLED, false);
    }

    /**
     * Sets whether user's full details are saved to a
     * <>code</>SharedPreferences<>code</> file.
     */
    public void prefSetInfoFilled(boolean infoFilled) {
        prefEdit().putBoolean(USER_INFO_FILLED, infoFilled).apply();
    }
}

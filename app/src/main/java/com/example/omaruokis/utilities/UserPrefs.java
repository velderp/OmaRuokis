package com.example.omaruokis.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPrefs extends InputChecker {
    private static final String PREF_USER = "UserInfo";
    private static final String USER_DOB = "DateOfBirth";
    private static final String USER_SEX = "Sex";
    private static final String USER_WEIGHT = "Weight";
    private static final String USER_HEIGHT = "Height";
    private static final String USER_PAL = "DefaultActivityLevel";
    private static final String USER_INFO_FILLED = "InfoFilled";
    private static final int MIN_HEIGHT = 24;
    private static final int MAX_HEIGHT = 272;
    public static final int MIN_WEIGHT = 1;
    public static final int MAX_WEIGHT = 635;
    public static final int MIN_YEAR = 1903;
    public static final int MAX_YEAR = 2200;
    private Context context;

    public UserPrefs(Context context) {
        this.context = context;
    }

    private SharedPreferences prefGet() {
        return context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor prefEdit() {
        return prefGet().edit();
    }

    public String prefGetUserDob() {
        return prefGet().getString(USER_DOB, "");
    }

    public boolean prefSetUserDob(String dateOfBirth) {
        if (checkDateValidity(dateOfBirth, MIN_YEAR, MAX_YEAR)
                && dateBeforeCurrent(dateOfBirth)) {
            prefEdit().putString(USER_DOB, formatDateToString(dateOfBirth)).apply();
            return true;
        }
        return false;
    }

    public String prefGetUserSex() {
        return prefGet().getString(USER_SEX, "M");
    }

    public void prefSetUserSex(String sex) {
        prefEdit().putString(USER_SEX, sex).apply();
    }

    public int prefGetUserWeight() {
        return prefGet().getInt(USER_WEIGHT, -1);
    }

    public boolean prefSetUserWeight(String weight) {
        if (checkInt(weight, MIN_WEIGHT, MAX_WEIGHT)) {
            prefEdit().putInt(USER_WEIGHT, Integer.parseInt(weight)).apply();
            return true;
        }
        return false;
    }

    public int prefGetUserHeight() {
        return prefGet().getInt(USER_HEIGHT, -1);
    }

    public boolean prefSetUserHeight(String height) {
        if (checkInt(height, MIN_HEIGHT, MAX_HEIGHT)) {
            prefEdit().putInt(USER_HEIGHT, Integer.parseInt(height)).apply();
            return true;
        }
        return false;
    }

    public int prefGetUserPal() {
        return prefGet().getInt(USER_PAL, 0);
    }

    public void prefSetUserPal(int activityLevel) {
        prefEdit().putInt(USER_PAL, activityLevel).apply();
    }

    public boolean prefGetInfoFilled() {
        return prefGet().getBoolean(USER_INFO_FILLED, false);
    }

    public void prefSetInfoFilled(boolean infoFilled) {
        prefEdit().putBoolean(USER_INFO_FILLED, infoFilled).apply();
    }
}

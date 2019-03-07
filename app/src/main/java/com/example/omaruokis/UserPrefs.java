package com.example.omaruokis;

import android.content.Context;
import android.content.SharedPreferences;

class UserPrefs {
    private static final String PREF_USER = "UserInfo";
    private static final String USER_DOB = "DateOfBirth";
    private static final String USER_SEX = "Sex";
    private static final String USER_WEIGHT = "Weight";
    private static final String USER_HEIGHT = "Height";
    private static final String USER_PAL = "DefaultActivityLevel";
    private static final String USER_INFO_FILLED = "InfoFilled";
    private Context context;

    UserPrefs(Context context) {
        this.context = context;
    }

    private SharedPreferences prefGet() {
        return context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor prefEdit() {
        return prefGet().edit();
    }

    private InputChecker checker() {
        return new InputChecker();
    }

    String prefGetUserDOB() {
        return prefGet().getString(USER_DOB, "");
    }

    boolean prefSetUserDOB(String dateOfBirth) {
        if (checker().checkDateValidity(dateOfBirth, 1900, 2100)) {
            prefEdit().putString(USER_DOB, checker().formatDate(dateOfBirth)).apply();
            return true;
        }
        return false;
    }

    String prefGetUserSex() {
        return prefGet().getString(USER_SEX, "M");
    }

    void prefSetUserSex(String sex) {
        prefEdit().putString(USER_SEX, sex).apply();
    }

    int prefGetUserHeight() {
        return prefGet().getInt(USER_HEIGHT, -1);
    }

    boolean prefSetUserHeight(String height) {
        if (checker().checkInt(height, 24, 272)) {
            prefEdit().putInt(USER_HEIGHT, Integer.parseInt(height)).apply();
            return true;
        }
        return false;
    }

    int prefGetUserWeight() {
        return prefGet().getInt(USER_WEIGHT, -1);
    }

    boolean prefSetUserWeight(String weight) {
        if (checker().checkInt(weight, 1, 635)) {
            prefEdit().putInt(USER_WEIGHT, Integer.parseInt(weight)).apply();
            return true;
        }
        return false;
    }

    int prefGetUserPAL() {
        return prefGet().getInt(USER_PAL, 0);
    }

    void prefSetUserPAL(int activityLevel) {
        prefEdit().putInt(USER_PAL, activityLevel).apply();
    }

    boolean prefGetInfoFilled() {
        return prefGet().getBoolean(USER_INFO_FILLED, false);
    }

    void prefSetInfoFilled(boolean infoFilled) {
        prefEdit().putBoolean(USER_INFO_FILLED, infoFilled).apply();
    }
}

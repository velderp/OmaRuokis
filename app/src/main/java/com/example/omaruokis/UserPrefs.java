package com.example.omaruokis;

import android.content.Context;
import android.content.SharedPreferences;

class UserPrefs {
    private static final String PREF_USER = "UserInfo";
    private static final String USER_DOB = "DateOfBirth";
    private static final String USER_SEX = "Sex";
    private static final String USER_WEIGHT = "Weight";
    private static final String USER_HEIGHT = "Height";
    private static final String USER_PAL = "ActivityLevel";
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

    String prefGetUserDOB() {
        return prefGet().getString(USER_DOB, "");
    }

    void prefSetUserDOB(String dateOfBirth) {
        prefEdit().putString(USER_DOB, dateOfBirth).apply();
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

    void prefSetUserHeight(int height) {
        prefEdit().putInt(USER_HEIGHT, height).apply();
    }

    int prefGetUserWeight() {
        return prefGet().getInt(USER_WEIGHT, -1);
    }

    void prefSetUserWeight(int weight) {
        prefEdit().putInt(USER_WEIGHT, weight).apply();
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

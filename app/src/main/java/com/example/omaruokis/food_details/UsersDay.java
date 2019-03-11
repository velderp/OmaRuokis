package com.example.omaruokis.food_details;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Annotated class for Room to create database table "users_day" from.
 * Stores users activity level and weight for the day.
 * @author Mika
 */
@Entity(tableName = "users_day")
public class UsersDay {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "DATE")
    private String date;
    @ColumnInfo(name = "ACTIVITYLEVEL")
    private int activityLevel;
    @ColumnInfo(name = "WEIGHT")
    private int weight;

    public UsersDay(@NonNull String date, int activityLevel, int weight) {
        this.date = date;
        this.activityLevel = activityLevel;
        this.weight = weight;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public int getWeight() {
        return weight;
    }
}

package com.example.omaruokis.food_details;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Annotated class for Room to create database from. Represent different "foodname_??" database tables acquired from Fineli's Open Data.
 * Implements Parcelable to pass Foodname objects with Intends. Refer to Fineli_Rel_19/descript.txt for all Fineli's table descriptions.
 * @author Mika
 */
public class FoodName implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "FOODID")
    private int foodId;

    @NonNull
    @ColumnInfo(name = "FOODNAME")
    private String foodName;

    @ColumnInfo(name = "LANG")
    private String lang;

    public FoodName(int foodId, @NonNull String foodName, String lang) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.lang = lang;
    }

    protected FoodName(Parcel in) {
        foodId = in.readInt();
        foodName = in.readString();
        lang = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(foodId);
        dest.writeString(foodName);
        dest.writeString(lang);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FoodName> CREATOR = new Creator<FoodName>() {
        @Override
        public FoodName createFromParcel(Parcel in) {
            return new FoodName(in);
        }

        @Override
        public FoodName[] newArray(int size) {
            return new FoodName[size];
        }
    };

    public int getFoodId() {
        return foodId;
    }

    @NonNull
    public String getFoodName() {
        return foodName;
    }

    public String getLang() {
        return lang;
    }
}

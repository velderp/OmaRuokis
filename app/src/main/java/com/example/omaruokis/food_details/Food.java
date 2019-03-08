package com.example.omaruokis.food_details;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "food_table")
public class Food {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "foodName")
    private String mFoodName;

    public Food(@NonNull int id, @NonNull String foodName) {
        this.mId = id;
        this.mFoodName = foodName;
    }

    public int getId() {
        return this.mId;
    }

    public String getFoodName() {
        return this.mFoodName;
    }
}


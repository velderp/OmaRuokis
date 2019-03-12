package com.example.omaruokis.food_details;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Annotated class for Room to create database table from.
 * Stores users favorite foods foodIds.
 * @author Mika
 */
@Entity(tableName = "favorite")
public class Favorite {

    @PrimaryKey
    @ColumnInfo(name = "FOODID")
    private int foodId;

    public Favorite(int foodId) {
        this.foodId = foodId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }
}

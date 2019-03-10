package com.example.omaruokis.food_details;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Annotated class for Room to create database from. Represent "foodname_SV" database table acquired from Fineli's Open Data.
 * Refer to Fineli_Rel_19/descript.txt for all Fineli's table descriptions.
 */
@Entity(tableName = "foodname_SV")
public class FoodNameSv extends FoodName{

    public FoodNameSv(int foodId, @NonNull String foodName, String lang) {
        super(foodId, foodName, lang);
    }
}

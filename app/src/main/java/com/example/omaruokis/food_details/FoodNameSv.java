package com.example.omaruokis.food_details;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "foodname_SV")
public class FoodNameSv extends FoodName{

    public FoodNameSv(int foodId, @NonNull String foodName, String lang) {
        super(foodId, foodName, lang);
    }
}

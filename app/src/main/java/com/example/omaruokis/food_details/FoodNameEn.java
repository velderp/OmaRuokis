package com.example.omaruokis.food_details;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "foodname_EN")
public class FoodNameEn extends FoodName{

    public FoodNameEn(int foodId, @NonNull String foodName, String lang) {
        super(foodId, foodName, lang);
    }
}

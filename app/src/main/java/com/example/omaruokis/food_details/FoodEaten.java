package com.example.omaruokis.food_details;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.omaruokis.utilities.DateHolder;

@Entity(tableName = "food_eaten")
public class FoodEaten {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "FOODEATENID")
    private int ID;
    @ColumnInfo(name = "DATE")
    private String date;
    @ColumnInfo(name = "FOODNAME")
    private String foodName;
    @ColumnInfo(name = "FOODID")
    private int foodId;
    @ColumnInfo(name = "ENERC")
    private Double enerc;
    @ColumnInfo(name = "CHOAVL")
    private Double choavl;
    @ColumnInfo(name = "FAT")
    private Double fat;
    @ColumnInfo(name = "PROT")
    private Double prot;
    @ColumnInfo(name = "FOODQUANTITY")
    private Double foodQuantity;

    public FoodEaten(String foodName, int foodId) {
        this.foodName = foodName;
        this.foodId = foodId;
        this.date = DateHolder.getInstance().currentDateToString();
        this.foodQuantity = 100.0;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodId(int foodId){
        this.foodId = foodId;
    }

    public void setEnerc(Double enerc) {
        this.enerc = enerc;
    }

    public void setChoavl(Double choavl) {
        this.choavl = choavl;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public void setProt(Double prot) {
        this.prot = prot;
    }

    public void setFoodQuantity(Double foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public int getID() {
        return ID;
    }

    public String getDate() {
        return date;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodId() {
        return foodId;
    }

    public Double getEnerc() {
        return enerc;
    }

    public Double getChoavl() {
        return choavl;
    }

    public Double getFat() {
        return fat;
    }

    public Double getProt() {
        return prot;
    }

    public Double getFoodQuantity() {
        return foodQuantity;
    }
}

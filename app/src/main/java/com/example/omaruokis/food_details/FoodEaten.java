package com.example.omaruokis.food_details;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Annotated class for Room to create database table from. Contains table columns from Fineli's Open Data:
 * "foodname_FI.FOODNAME", "{foodname_FI, component_value}.FOODID" and "component_value" "BESTLOC"
 * datavalues which "EUFDNAME" datavalues were "ENERC", "CHOAVL", "PROT".
 * Refer to Fineli_Rel_19/descript.txt for all Fineli's table descriptions.
 * Class represents occurrence of food eating. It stores main nutrients and quantity of the meal.
 */
import com.example.omaruokis.utilities.DateHolder;

/**
 * Annotated class for Room to create database table "food_eaten" from.
 * Stores users food consumed information for the day. Contains table columns from Fineli's Open Data:
 * "foodname_FI.FOODNAME", "{foodname_FI, component_value}.FOODID" and "component_value.BESTLOC" data values,
 * which "component_value.EUFDNAME" data values were "ENERC", "CHOAVL", "FAT", "PROT".
 * @author Mika
 */
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
        this.date = DateHolder.getInstance().selectedDateToString();
        this.foodQuantity = 100.0;
    }

    /**
     * Get the energy of the food amount.
     * @return Double energy of consumed food quantity in kJ.
     */
    public Double calculateTotalEnergy(){
        if(foodQuantity > 0){
            return enerc * (foodQuantity / 100.0);
        } else {
            return 0.0;
        }
    }

    /**
     * Get the carbohydrate quantity of the food amount.
     * @return Double carbohydrates of consumed food quantity in g.
     */
    public Double calculateTotalCarbohydrates(){
        if(foodQuantity > 0){
            return choavl * (foodQuantity / 100.0);
        } else {
            return 0.0;
        }
    }

    /**
     * Get the protein quantity of the food amount.
     * @return Double carbohydrates of consumed food quantity in g.
     */
    public Double calculateTotalProteins(){
        if(foodQuantity > 0){
            return prot * (foodQuantity / 100.0);
        } else {
            return 0.0;
        }
    }

    /**
     * Get the lipid  quantity of the food amount.
     * @return Double lipids of consumed food quantity in g.
     */
    public Double calculateTotalFat(){
        if(foodQuantity > 0){
            return fat * (foodQuantity / 100.0);
        } else {
            return 0.0;
        }
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Set the date of eating.
     * @param date String date which food was eaten.
     */
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

    /**
     * Set the amount of food eaten.
     * @param foodQuantity Double amount of food eaten.
     */
    public void setFoodQuantity(Double foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public int getID() {
        return ID;
    }

    /**
     * Get the date of eating.
     * @return String date which food was eaten.
     */
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

    /**
     * Get the amount of food eaten.
     * @return Double amount of food eaten.
     */
    public Double getFoodQuantity() {
        return foodQuantity;
    }
}

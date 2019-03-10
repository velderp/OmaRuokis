package com.example.omaruokis.food_details;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Annotated class for Room to create database from. Represent "component_value" database table acquired from Fineli's Open Data.
 * Refer to Fineli_Rel_19/descript.txt for all Fineli's table descriptions.
 */
@Entity(tableName = "component_value", primaryKeys = {"FOODID", "EUFDNAME"})
public class ComponentValue {

    @ColumnInfo(name = "FOODID")
    private int foodId;

    @NonNull
    @ColumnInfo(name = "EUFDNAME")
    private String eufdName;

    @ColumnInfo(name = "BESTLOC")
    private double bestLoc;

    @ColumnInfo(name = "ACQTYPE")
    private String acqType;

    @ColumnInfo(name = "METHTYPE")
    private String methType;

    @ColumnInfo(name = "PUBLID")
    private int pubId;

    public ComponentValue(int foodId, String eufdName, double bestLoc, String acqType, String methType, int pubId) {
        this.foodId = foodId;
        this.eufdName = eufdName;
        this.bestLoc = bestLoc;
        this.acqType = acqType;
        this.methType = methType;
        this.pubId = pubId;
    }

    public int getFoodId() {
        return foodId;
    }

    @NonNull
    public String getEufdName() {
        return eufdName;
    }

    public double getBestLoc() {
        return bestLoc;
    }

    public String getAcqType() {
        return acqType;
    }

    public String getMethType() {
        return methType;
    }

    public int getPubId() {
        return pubId;
    }
}

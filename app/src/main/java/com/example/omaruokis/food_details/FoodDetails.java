package com.example.omaruokis.food_details;

import android.arch.persistence.room.ColumnInfo;

/**
 * Annotated class for Room to create objects from. It is not stored at database. It is composed from
 * other tables with sql queries, for usage in application. Contains table columns from Fineli's Open Data:
 * "eufdname_FI.DESCRIPT", "component_value.BESTLOC", "component.COMPUNIT"
 * @author Mika
 */
public class FoodDetails {

    //food component name
    @ColumnInfo(name = "DESCRIPT")
    private String descript;
    @ColumnInfo(name = "BESTLOC")
    private Double bestloc;
    @ColumnInfo(name = "COMPUNIT")
    private String compUnit;

    public FoodDetails(String descript, Double bestloc, String compUnit) {
        this.descript = descript;
        this.bestloc = bestloc;
        this.compUnit = compUnit;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setBestloc(Double bestloc) {
        this.bestloc = bestloc;
    }

    public String getDescript() {
        return descript;
    }

    public Double getBestloc() {
        return bestloc;
    }

    public String getCompUnit() {
        return compUnit;
    }
}

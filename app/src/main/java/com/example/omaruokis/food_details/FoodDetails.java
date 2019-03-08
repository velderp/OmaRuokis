package com.example.omaruokis.food_details;

import android.arch.persistence.room.ColumnInfo;

public class FoodDetails {
    //eufdname_FI.DESCRIPT, component_value.BESTLOC

    @ColumnInfo(name = "DESCRIPT")
    private String descript;
    @ColumnInfo(name = "BESTLOC")
    private Double bestloc;

    public FoodDetails(String descript, Double bestloc) {
        this.descript = descript;
        this.bestloc = bestloc;
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
}

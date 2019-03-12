package com.example.omaruokis.food_details;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Annotated class for Room to create database from. Represent "component" database table acquired from Fineli's Open Data.
 * Refer to Fineli_Rel_19/descript.txt for all Fineli's table descriptions.
 * @author Mika
 */
@Entity(tableName = "component")
public class Component {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "EUFDNAME")
    private String eufdName;

    @ColumnInfo(name = "COMPUNIT")
    private String compUnit;

    @ColumnInfo(name = "CMPCLASS")
    private String cmpClass;

    @ColumnInfo(name = "CMPCLASSP")
    private String cmpClassp;

    public Component(@NonNull String eufdName, String compUnit, String cmpClass, String cmpClassp) {
        this.eufdName = eufdName;
        this.compUnit = compUnit;
        this.cmpClass = cmpClass;
        this.cmpClassp = cmpClassp;
    }

    @NonNull
    public String getEufdName() {
        return eufdName;
    }

    public String getCompUnit() {
        return compUnit;
    }

    public String getCmpClass() {
        return cmpClass;
    }

    public String getCmpClassp() {
        return cmpClassp;
    }
}

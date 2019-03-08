package com.example.omaruokis.food_details;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "eufdname_FI")
public class EufdnameFi {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "THSCODE")
    private String thsCode;

    @ColumnInfo(name = "DESCRIPT")
    private String descrpt;

    @ColumnInfo(name = "LANG")
    private String lang;

    public EufdnameFi(@NonNull String thsCode, String descrpt, String lang) {
        this.thsCode = thsCode;
        this.descrpt = descrpt;
        this.lang = lang;
    }

    @NonNull
    public String getThsCode() {
        return thsCode;
    }

    public String getDescrpt() {
        return descrpt;
    }

    public String getLang() {
        return lang;
    }
}

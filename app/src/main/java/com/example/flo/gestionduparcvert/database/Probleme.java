package com.example.flo.gestionduparcvert.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by flo on 25/11/2017.
 *
 */
@DatabaseTable(tableName="Problemes")
public class Probleme implements Serializable {
    public static final String PROBLEME_ID="id";
    public static final String PROBLEME_TYPE="type";
    public static final String PROBLEME_ADRESSE="adresse";
    public static final String PROBLEME_lONGITUDE="longitude";
    public static final String PROBLEME_lATITUDE="latitude";
    public static final String PROBLEME_DESCRIPTION="description";

    @DatabaseField(columnName = PROBLEME_ID,generatedId = true)
    private Integer mid;
    @DatabaseField(columnName = PROBLEME_TYPE)
    private String type;
    @DatabaseField(columnName = PROBLEME_ADRESSE)
    private String adresse;
    @DatabaseField(columnName = PROBLEME_lONGITUDE)
    private Double longitude;
    @DatabaseField(columnName = PROBLEME_lATITUDE)
    private Double latitude;

    @DatabaseField(columnName = PROBLEME_DESCRIPTION)
    private String description;

    public Probleme(){

    }

    public Probleme(String type, String adresse,Double longitude, Double latitude, String description){
        this.type = type;
        this.adresse = adresse;
        this.longitude=longitude;
        this.latitude=latitude;
        this.description=description;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

public class WardDistrictProvincePersonal {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("location")
    private String location;

    public WardDistrictProvincePersonal(int id, String name, String type, String location) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

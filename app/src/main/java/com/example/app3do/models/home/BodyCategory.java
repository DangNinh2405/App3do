package com.example.app3do.models.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyCategory {

    @SerializedName("data")
    private List<DataCategory> dataCategory;

    public List<DataCategory> getDataCategory() {
        return dataCategory;
    }

    public void setDataCategory(List<DataCategory> dataCategory) {
        this.dataCategory = dataCategory;
    }
}

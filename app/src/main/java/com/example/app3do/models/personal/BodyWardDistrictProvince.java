package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyWardDistrictProvince {

    @SerializedName("data")
    private List<WardDistrictProvincePersonal> data;

    public List<WardDistrictProvincePersonal> getData() {
        return data;
    }

    public void setData(List<WardDistrictProvincePersonal> data) {
        this.data = data;
    }
}

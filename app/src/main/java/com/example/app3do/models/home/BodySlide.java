package com.example.app3do.models.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodySlide {

    @SerializedName("data")
    private List<DataSlide> dataSlide;


    public List<DataSlide> getDataSlide() {
        return dataSlide;
    }

    public void setDataSlide(List<DataSlide> dataSlide) {
        this.dataSlide = dataSlide;
    }
}

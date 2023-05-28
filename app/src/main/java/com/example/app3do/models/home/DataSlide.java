package com.example.app3do.models.home;

import com.google.gson.annotations.SerializedName;

public class DataSlide {

    @SerializedName("link_img")
    private String linkImg;

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }
}

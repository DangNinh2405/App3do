package com.example.app3do.models.product;

import com.google.gson.annotations.SerializedName;

public class Avatar {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

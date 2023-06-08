package com.example.app3do.models.product;

import com.google.gson.annotations.SerializedName;

public class Avatar {

    @SerializedName("url")
    private String url;

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

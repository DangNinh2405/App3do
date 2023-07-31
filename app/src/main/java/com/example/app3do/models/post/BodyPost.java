package com.example.app3do.models.post;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyPost {
    @SerializedName("meta")
    private MetaPost meta;

    @SerializedName("data")
    private List<DataPost> data;

    public MetaPost getMeta() {
        return meta;
    }

    public void setMeta(MetaPost meta) {
        this.meta = meta;
    }

    public List<DataPost> getData() {
        return data;
    }

    public void setData(List<DataPost> data) {
        this.data = data;
    }
}

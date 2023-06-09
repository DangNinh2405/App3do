package com.example.app3do.models.marketing;

import com.example.app3do.models.personal.DataPersonal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyMarketing {
    @SerializedName("meta")
    private MetaMarketing meta;

    @SerializedName("data")
    private List<DataPersonal> data;

    public MetaMarketing getMeta() {
        return meta;
    }

    public void setMeta(MetaMarketing meta) {
        this.meta = meta;
    }

    public List<DataPersonal> getData() {
        return data;
    }

    public void setData(List<DataPersonal> data) {
        this.data = data;
    }
}

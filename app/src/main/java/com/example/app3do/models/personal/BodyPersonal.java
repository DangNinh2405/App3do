package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

public class BodyPersonal {
    @SerializedName("meta")
    private MetaPersonal meta;

    @SerializedName("data")
    private DataPersonal data;

    public MetaPersonal getMeta() {
        return meta;
    }

    public void setMeta(MetaPersonal meta) {
        this.meta = meta;
    }

    public DataPersonal getData() {
        return data;
    }

    public void setData(DataPersonal data) {
        this.data = data;
    }
}

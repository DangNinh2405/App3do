package com.example.app3do.models.notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyNotification {
    @SerializedName("meta")
    private MetaNotification meta;

    @SerializedName("data")
    private List<DataNotification> data;

    public MetaNotification getMeta() {
        return meta;
    }

    public void setMeta(MetaNotification meta) {
        this.meta = meta;
    }

    public List<DataNotification> getData() {
        return data;
    }

    public void setData(List<DataNotification> data) {
        this.data = data;
    }
}

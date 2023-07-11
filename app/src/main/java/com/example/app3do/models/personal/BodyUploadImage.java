package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

public class BodyUploadImage {
    @SerializedName("data")
    private DataUploadImage data;

    public DataUploadImage getData() {
        return data;
    }

    public void setData(DataUploadImage data) {
        this.data = data;
    }
}

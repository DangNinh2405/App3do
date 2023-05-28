package com.example.app3do.models.verify;

import com.google.gson.annotations.SerializedName;

public class BodyVerify {

    @SerializedName("data")
    DataVerify data;

    public DataVerify getData() {
        return data;
    }

    public void setData(DataVerify data) {
        this.data = data;
    }
}

package com.example.app3do.models.report;

import com.google.gson.annotations.SerializedName;

public class BodyReports {
    @SerializedName("data")
    private DataReports data;

    public DataReports getData() {
        return data;
    }

    public void setData(DataReports data) {
        this.data = data;
    }
}

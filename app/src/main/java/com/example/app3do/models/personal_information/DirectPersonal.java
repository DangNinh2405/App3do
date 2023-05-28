package com.example.app3do.models.personal_information;

import com.google.gson.annotations.SerializedName;

public class DirectPersonal {

    @SerializedName("total")
    private String total;

    @SerializedName("total_display")
    private String total_display;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal_display() {
        return total_display;
    }

    public void setTotal_display(String total_display) {
        this.total_display = total_display;
    }
}

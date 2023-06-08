package com.example.app3do.models.order;

import com.google.gson.annotations.SerializedName;

public class CreateAt {
    @SerializedName("date")
    private String date;

    @SerializedName("timezone")
    private String timezone;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}

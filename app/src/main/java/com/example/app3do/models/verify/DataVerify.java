package com.example.app3do.models.verify;

import com.google.gson.annotations.SerializedName;

public class DataVerify {

    @SerializedName("id")
    private int id;

    @SerializedName("phone")
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

package com.example.app3do.models.account;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BodyLogin implements Serializable {

    @SerializedName("data")
    DataLogin dataLogin;

    public DataLogin getDataLogin() {
        return dataLogin;
    }

    public void setDataLogin(DataLogin dataLogin) {
        this.dataLogin = dataLogin;
    }
}

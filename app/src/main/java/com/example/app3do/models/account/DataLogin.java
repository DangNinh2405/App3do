package com.example.app3do.models.account;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataLogin implements Serializable {

    @SerializedName("user_id")
    private int userId;

    @SerializedName("access_token")
    private String accessToken;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

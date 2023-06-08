package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

public class DeleteAddress {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("id")
    private int id;

    public DeleteAddress(String accessToken, int id) {
        this.accessToken = accessToken;
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

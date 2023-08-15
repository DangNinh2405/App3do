package com.example.app3do.models.exchange_points;

import com.google.gson.annotations.SerializedName;

public class ExchangePoints {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("money")
    private int money;

    public ExchangePoints(String accessToken, int money) {
        this.money = money;
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return this.accessToken;
    }
}

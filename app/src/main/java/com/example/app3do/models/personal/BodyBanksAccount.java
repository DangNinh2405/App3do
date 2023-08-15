package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyBanksAccount {
    @SerializedName("data")
    private List<BanksPersonal> data;

    public List<BanksPersonal> getData() {
        return data;
    }
}

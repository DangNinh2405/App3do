package com.example.app3do.models.report;

import com.google.gson.annotations.SerializedName;

public class UsersReports {
    @SerializedName("total")
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

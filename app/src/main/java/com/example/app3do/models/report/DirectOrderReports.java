package com.example.app3do.models.report;

import com.google.gson.annotations.SerializedName;

public class DirectOrderReports {

    @SerializedName("total")
    private int total;

    @SerializedName("money")
    private int money;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}

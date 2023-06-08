package com.example.app3do.models.report;

import com.google.gson.annotations.SerializedName;

public class OrderReports {

    @SerializedName("owner")
    private OwnerOrderReports owner;

    @SerializedName("direct")
    private DirectOrderReports direct;

    @SerializedName("all")
    private DirectOrderReports all;

    public DirectOrderReports getDirect() {
        return direct;
    }

    public void setDirect(DirectOrderReports direct) {
        this.direct = direct;
    }

    public DirectOrderReports getAll() {
        return all;
    }

    public void setAll(DirectOrderReports all) {
        this.all = all;
    }

    public OwnerOrderReports getOwner() {
        return owner;
    }

    public void setOwner(OwnerOrderReports owner) {
        this.owner = owner;
    }
}

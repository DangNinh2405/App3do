package com.example.app3do.models.report;

import com.google.gson.annotations.SerializedName;

public class CommissionReports {
    @SerializedName("owner")
    private OwnerComissionReports owner;

    @SerializedName("direct")
    private OwnerComissionReports direct;

    @SerializedName("all")
    private OwnerComissionReports all;

    public OwnerComissionReports getOwner() {
        return owner;
    }

    public void setOwner(OwnerComissionReports owner) {
        this.owner = owner;
    }

    public OwnerComissionReports getDirect() {
        return direct;
    }

    public void setDirect(OwnerComissionReports direct) {
        this.direct = direct;
    }

    public OwnerComissionReports getAll() {
        return all;
    }

    public void setAll(OwnerComissionReports all) {
        this.all = all;
    }
}

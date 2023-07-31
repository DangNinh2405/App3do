package com.example.app3do.models.marketing;

import com.example.app3do.models.product.Pagination;
import com.example.app3do.models.report.PointReports;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MetaMarketing {
    @SerializedName("pagination")
    private Pagination pagination;

    @SerializedName("point")
    private PointReports point;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public PointReports getPoint() {
        return point;
    }

    public void setPoint(PointReports point) {
        this.point = point;
    }
}

package com.example.app3do.models.report;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataReports {

    @SerializedName("order")
    private OrderReports order;

    @SerializedName("commission")
    private CommissionReports commission;

    @SerializedName("point")
    private PointReports point;

    @SerializedName("users")
    private UsersReports users;

    public OrderReports getOrder() {
        return order;
    }

    public void setOrder(OrderReports order) {
        this.order = order;
    }

    public CommissionReports getCommission() {
        return commission;
    }

    public void setCommission(CommissionReports commission) {
        this.commission = commission;
    }

    public PointReports getPoint() {
        return point;
    }

    public void setPoint(PointReports point) {
        this.point = point;
    }

    public UsersReports getUsers() {
        return users;
    }

    public void setUsers(UsersReports users) {
        this.users = users;
    }
}

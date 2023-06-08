package com.example.app3do.models.report;

import com.google.gson.annotations.SerializedName;

public class PointReports {

    @SerializedName("total_money_member")
    private int totalMoneyMember;

    @SerializedName("total_money_member_report")
    private int totalMoneyMemberReport;

    @SerializedName("total_money_member_f1")
    private int totalMoneyMemberF1;

    @SerializedName("total_point")
    private int totalPoint;

    @SerializedName("total_point_member")
    private int totalPointMember;

    @SerializedName("total_point_f1")
    private int totalPointF1;

    @SerializedName("moneyCommissionGroupOnly")
    private int moneyCommissionGroupOnly;

    @SerializedName("countGroupOnly")
    private int countGroupOnly;

    @SerializedName("moneyGroupOnly")
    private int moneyGroupOnly;

    @SerializedName("moneyGroupChi")
    private int moneyGroupChi;

    public int getTotalMoneyMember() {
        return totalMoneyMember;
    }

    public void setTotalMoneyMember(int totalMoneyMember) {
        this.totalMoneyMember = totalMoneyMember;
    }

    public int getTotalMoneyMemberReport() {
        return totalMoneyMemberReport;
    }

    public void setTotalMoneyMemberReport(int totalMoneyMemberReport) {
        this.totalMoneyMemberReport = totalMoneyMemberReport;
    }

    public int getTotalMoneyMemberF1() {
        return totalMoneyMemberF1;
    }

    public void setTotalMoneyMemberF1(int totalMoneyMemberF1) {
        this.totalMoneyMemberF1 = totalMoneyMemberF1;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public int getTotalPointMember() {
        return totalPointMember;
    }

    public void setTotalPointMember(int totalPointMember) {
        this.totalPointMember = totalPointMember;
    }

    public int getTotalPointF1() {
        return totalPointF1;
    }

    public void setTotalPointF1(int totalPointF1) {
        this.totalPointF1 = totalPointF1;
    }

    public int getMoneyCommissionGroupOnly() {
        return moneyCommissionGroupOnly;
    }

    public void setMoneyCommissionGroupOnly(int moneyCommissionGroupOnly) {
        this.moneyCommissionGroupOnly = moneyCommissionGroupOnly;
    }

    public int getCountGroupOnly() {
        return countGroupOnly;
    }

    public void setCountGroupOnly(int countGroupOnly) {
        this.countGroupOnly = countGroupOnly;
    }

    public int getMoneyGroupOnly() {
        return moneyGroupOnly;
    }

    public void setMoneyGroupOnly(int moneyGroupOnly) {
        this.moneyGroupOnly = moneyGroupOnly;
    }

    public int getMoneyGroupChi() {
        return moneyGroupChi;
    }

    public void setMoneyGroupChi(int moneyGroupChi) {
        this.moneyGroupChi = moneyGroupChi;
    }
}

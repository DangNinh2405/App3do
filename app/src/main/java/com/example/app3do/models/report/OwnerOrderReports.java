package com.example.app3do.models.report;

import com.google.gson.annotations.SerializedName;

public class OwnerOrderReports {

    @SerializedName("total")
    private int total;

    @SerializedName("money")
    private int money;

    @SerializedName("total_medium_referre")
    private int totalMediumReferre;

    @SerializedName("Reward_point")
    private int RewardPoint;

    @SerializedName("total_referre_all")
    private int totalReferreAll;

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

    public int getTotalMediumReferre() {
        return totalMediumReferre;
    }

    public void setTotalMediumReferre(int totalMediumReferre) {
        this.totalMediumReferre = totalMediumReferre;
    }

    public int getRewardPoint() {
        return RewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        RewardPoint = rewardPoint;
    }

    public int getTotalReferreAll() {
        return totalReferreAll;
    }

    public void setTotalReferreAll(int totalReferreAll) {
        this.totalReferreAll = totalReferreAll;
    }
}

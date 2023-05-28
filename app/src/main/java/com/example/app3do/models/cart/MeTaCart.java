package com.example.app3do.models.cart;

import com.google.gson.annotations.SerializedName;

public class MeTaCart {

    @SerializedName("total_product")
    private int totalProduct;

    @SerializedName("total_money")
    private int totalMoney;


    @SerializedName("total_money_origin")
    private int totalMoneyOrigin;

    @SerializedName("total_discount")
    private int totalDiscount;

    @SerializedName("total_direct_commission")
    private int totalDirectCommission;

    @SerializedName("shipping_fee")
    private int shippingFee;

    @SerializedName("total_point")
    private int totalPoint;

    public int getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(int shippingFee) {
        this.shippingFee = shippingFee;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getTotalMoneyOrigin() {
        return totalMoneyOrigin;
    }

    public void setTotalMoneyOrigin(int totalMoneyOrigin) {
        this.totalMoneyOrigin = totalMoneyOrigin;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(int totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public int getTotalDirectCommission() {
        return totalDirectCommission;
    }

    public void setTotalDirectCommission(int totalDirectCommission) {
        this.totalDirectCommission = totalDirectCommission;
    }
}

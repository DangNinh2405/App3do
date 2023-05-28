package com.example.app3do.models.product;

import com.google.gson.annotations.SerializedName;

public class MinPricePolicy {

    @SerializedName("price")
    private String price;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("real_price")
    private int realPrice;

    @SerializedName("display_real_price")
    private String displayRealPrice;

    @SerializedName("discount_percent")
    private float discountPercent;

    @SerializedName("discount_percent_round")
    private int discountPercentRound;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(int realPrice) {
        this.realPrice = realPrice;
    }

    public String getDisplayRealPrice() {
        return displayRealPrice;
    }

    public void setDisplayRealPrice(String displayRealPrice) {
        this.displayRealPrice = displayRealPrice;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getDiscountPercentRound() {
        return discountPercentRound;
    }

    public void setDiscountPercentRound(int discountPercentRound) {
        this.discountPercentRound = discountPercentRound;
    }
}

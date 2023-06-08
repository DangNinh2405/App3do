package com.example.app3do.models.product;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataProduct implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("specifications")
    private String specifications;

    @SerializedName("price")
    private int price;

    @SerializedName("discount_percent")
    private float discountPercent;

    @SerializedName("discount_price")
    private int discountPrice;

    @SerializedName("point")
    private int point;

    @SerializedName("min_price_policy")
    private MinPricePolicy minPricePolicy;

    @SerializedName("avatar")
    private Avatar avatar;

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public MinPricePolicy getMinPricePolicy() {
        return minPricePolicy;
    }

    public void setMinPricePolicy(MinPricePolicy minPricePolicy) {
        this.minPricePolicy = minPricePolicy;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}

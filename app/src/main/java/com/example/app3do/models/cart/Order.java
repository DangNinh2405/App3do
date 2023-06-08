package com.example.app3do.models.cart;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address_id")
    private int addressId;

    @SerializedName("payment_type")
    private String paymentType;

    @SerializedName("discount_type")
    private int discountType;

    @SerializedName("note")
    private String note;

    @SerializedName("access_token")
    private String accessToken;

    public Order(String name, String phone, int addressId, String paymentType, int discountType, String note, String accessToken) {
        this.name = name;
        this.phone = phone;
        this.addressId = addressId;
        this.paymentType = paymentType;
        this.discountType = discountType;
        this.note = note;
        this.accessToken = accessToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

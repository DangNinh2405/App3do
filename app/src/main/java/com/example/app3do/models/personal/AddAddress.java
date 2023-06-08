package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

public class AddAddress {
    @SerializedName("is_main")
    private String isMain;

    @SerializedName("title")
    private String title;

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("ward_id")
    private int wardId;

    @SerializedName("district_id")
    private int districtId;

    @SerializedName("province_id")
    private int provinceId;

    @SerializedName("access_token")
    private String accessToken;

    public AddAddress(String isMain, String title, String name, String phone, String address, int wardId, int districtId, int provinceId, String accessToken) {
        this.isMain = isMain;
        this.title = title;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.wardId = wardId;
        this.districtId = districtId;
        this.provinceId = provinceId;
        this.accessToken = accessToken;
    }

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressesPersonal implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("is_main")
    private int isMain;

    @SerializedName("address")
    private String address;

    @SerializedName("ward")
    private WardDistrictProvincePersonal ward;

    @SerializedName("district")
    private WardDistrictProvincePersonal district;

    @SerializedName("province")
    private WardDistrictProvincePersonal province;

    public int getIsMain() {
        return isMain;
    }

    public void setIsMain(int isMain) {
        this.isMain = isMain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public WardDistrictProvincePersonal getWard() {
        return ward;
    }

    public void setWard(WardDistrictProvincePersonal ward) {
        this.ward = ward;
    }

    public WardDistrictProvincePersonal getDistrict() {
        return district;
    }

    public void setDistrict(WardDistrictProvincePersonal district) {
        this.district = district;
    }

    public WardDistrictProvincePersonal getProvince() {
        return province;
    }

    public void setProvince(WardDistrictProvincePersonal province) {
        this.province = province;
    }
}

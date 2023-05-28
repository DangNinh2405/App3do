package com.example.app3do.models.register;

import com.google.gson.annotations.SerializedName;

public class Register {
    @SerializedName("referral_code")
    private String referralCode;

    @SerializedName("phone")
    private String phoneNumber;

    @SerializedName("name")
    private String name;

    @SerializedName("password")
    private String password;

    @SerializedName("re_password")
    private String rePassword;

    public Register(String referralCode, String name, String phoneNumber, String password, String rePassword) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.referralCode = referralCode;
        this.password = password;
        this.rePassword = rePassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}

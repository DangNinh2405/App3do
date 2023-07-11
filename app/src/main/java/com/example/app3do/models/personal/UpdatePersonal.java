package com.example.app3do.models.personal;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class UpdatePersonal {
    @SerializedName("name")
    private String name;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("gender")
    private int gender;

    @SerializedName("birthdays")
    private String birthdays;

    @SerializedName("address")
    private String address;

    @SerializedName("email")
    private String email;

    @SerializedName("identity_number")
    private String identityNumber;

    @SerializedName("identity_front_image")
    private String identityFrontImage;

    @SerializedName("identity_back_image")
    private String identityBackImage;

    @SerializedName("access_token")
    private String accessToken;

    public UpdatePersonal(String name, String avatar, int gender, String birthdays, String address, String email, String identityNumber, String identityFrontImage, String identityBackImage, String accessToken) {
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
        this.birthdays = birthdays;
        this.address = address;
        this.email = email;
        this.identityNumber = identityNumber;
        this.identityFrontImage = identityFrontImage;
        this.identityBackImage = identityBackImage;
        this.accessToken = accessToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthdays() {
        return birthdays;
    }

    public void setBirthdays(String birthdays) {
        this.birthdays = birthdays;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getIdentityFrontImage() {
        return identityFrontImage;
    }

    public void setIdentityFrontImage(String identityFrontImage) {
        this.identityFrontImage = identityFrontImage;
    }

    public String getIdentityBackImage() {
        return identityBackImage;
    }

    public void setIdentityBackImage(String identityBackImage) {
        this.identityBackImage = identityBackImage;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

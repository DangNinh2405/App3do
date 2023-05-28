package com.example.app3do.models.personal_information;

import com.google.gson.annotations.SerializedName;

public class IdentityPersonal {

    @SerializedName("number")
    private String number;

    @SerializedName("front_image")
    private String frontImage;

    @SerializedName("back_image")
    private String backImage;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }
}

package com.example.app3do.models.change_referral_code;

import com.google.gson.annotations.SerializedName;

public class ChangeReferralCode {
    @SerializedName("code")
    private String code;

    @SerializedName("access_token")
    private String accessToken;

    public ChangeReferralCode(String code, String accessToken){
        this.code = code;
        this.accessToken = accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

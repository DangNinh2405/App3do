package com.example.app3do.models.account;

import com.google.gson.annotations.SerializedName;

public class ChangePassword {
    @SerializedName("password")
    private String password;

    @SerializedName("old_password")
    private String oldPassword;

    @SerializedName("access_token")
    private String accessToken;

    public ChangePassword(String password, String oldPassword, String accessToken) {
        this.oldPassword = oldPassword;
        this.password = password;
        this.accessToken = accessToken;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

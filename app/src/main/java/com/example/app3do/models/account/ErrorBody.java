package com.example.app3do.models.account;

import com.google.gson.annotations.SerializedName;

public class ErrorBody {
    @SerializedName("code")
    private int code;

    @SerializedName("error")
    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

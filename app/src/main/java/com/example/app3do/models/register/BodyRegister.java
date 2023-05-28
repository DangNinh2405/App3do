package com.example.app3do.models.register;

import com.google.gson.annotations.SerializedName;

public class BodyRegister {
    @SerializedName("code")
    private int code;

    @SerializedName("version")
    private int version;

    @SerializedName("data")
    private DataRegister data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public DataRegister getData() {
        return data;
    }

    public void setData(DataRegister data) {
        this.data = data;
    }
}

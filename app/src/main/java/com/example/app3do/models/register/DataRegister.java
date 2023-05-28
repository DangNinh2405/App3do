package com.example.app3do.models.register;

import com.google.gson.annotations.SerializedName;

public class DataRegister {
    @SerializedName("id")
    private int id;

    public DataRegister(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

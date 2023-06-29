package com.example.app3do.models.firebase;

import com.example.app3do.models.verify.RawDataFirebaseMessage;
import com.google.gson.annotations.SerializedName;

public class RawFirebaseMessage {
    @SerializedName("to")
    private String to;

    @SerializedName("data")
    private RawDataFirebaseMessage data;

    public RawFirebaseMessage(String to, RawDataFirebaseMessage data) {
        this.to = to;
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public RawDataFirebaseMessage getData() {
        return data;
    }

    public void setData(RawDataFirebaseMessage data) {
        this.data = data;
    }
}

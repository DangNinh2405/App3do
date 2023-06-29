package com.example.app3do.models.firebase;

import com.google.gson.annotations.SerializedName;

public class BodyFirebaseMessage {
    @SerializedName("multicast_id")
    private int multicast_id;

    @SerializedName("success")
    private int success;

    @SerializedName("failure")
    private int failure;

    public int getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(int multicast_id) {
        this.multicast_id = multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }
}

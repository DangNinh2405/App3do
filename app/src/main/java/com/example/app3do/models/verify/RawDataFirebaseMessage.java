package com.example.app3do.models.verify;

import com.google.gson.annotations.SerializedName;

public class RawDataFirebaseMessage {
    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    public RawDataFirebaseMessage(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

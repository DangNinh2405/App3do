package com.example.app3do.models.notification;

import com.example.app3do.models.order.CreateAt;
import com.google.gson.annotations.SerializedName;

public class DataNotification {
    @SerializedName("id")
    private int id;

    @SerializedName("type")
    private String type;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("read_at")
    private int readAt;

    @SerializedName("created_at")
    private CreateAt createAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReadAt() {
        return readAt;
    }

    public void setReadAt(int readAt) {
        this.readAt = readAt;
    }

    public CreateAt getCreateAt() {
        return createAt;
    }

    public void setCreateAt(CreateAt createAt) {
        this.createAt = createAt;
    }
}

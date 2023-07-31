package com.example.app3do.models.post;

import com.example.app3do.models.order.CreateAt;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataPost implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    @SerializedName("content")
    private String content;

    @SerializedName("created_at")
    private CreateAt createdAt;

    @SerializedName("link")
    private String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CreateAt getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(CreateAt createdAt) {
        this.createdAt = createdAt;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

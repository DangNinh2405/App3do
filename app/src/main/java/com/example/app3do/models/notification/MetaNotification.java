package com.example.app3do.models.notification;

import com.example.app3do.models.product.Pagination;
import com.google.gson.annotations.SerializedName;

public class MetaNotification {
    @SerializedName("unread")
    private int unread;

    @SerializedName("pagination")
    private Pagination pagination;

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}

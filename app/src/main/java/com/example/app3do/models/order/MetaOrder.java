package com.example.app3do.models.order;

import com.example.app3do.models.product.Pagination;
import com.google.gson.annotations.SerializedName;

public class MetaOrder {
    @SerializedName("pagination")
    private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}

package com.example.app3do.models.product;

import com.google.gson.annotations.SerializedName;

public class MetaProduct {
    @SerializedName("pagination")
    private Pagination pagination;


    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}

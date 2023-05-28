package com.example.app3do.models.product;

import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("total")
    private int total;

    @SerializedName("count")
    private int count;

    @SerializedName("total_pages")
    private int total_pages;

    public int getTotal() {
        return total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

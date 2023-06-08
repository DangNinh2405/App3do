package com.example.app3do.models.order;

import com.example.app3do.models.product.DataProduct;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataOrder {
    @SerializedName("id")
    private int id;

    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("created_at")
    private CreateAt createdAt;

    @SerializedName("total_point")
    private int total_point;

    @SerializedName("total_money")
    private int total_money;

    @SerializedName("products")
    private List<DataProduct> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CreateAt getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(CreateAt createdAt) {
        this.createdAt = createdAt;
    }

    public int getTotal_point() {
        return total_point;
    }

    public void setTotal_point(int total_point) {
        this.total_point = total_point;
    }

    public int getTotal_money() {
        return total_money;
    }

    public void setTotal_money(int total_money) {
        this.total_money = total_money;
    }

    public List<DataProduct> getProducts() {
        return products;
    }

    public void setProducts(List<DataProduct> products) {
        this.products = products;
    }
}

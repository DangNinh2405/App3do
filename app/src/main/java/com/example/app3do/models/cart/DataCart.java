package com.example.app3do.models.cart;

import com.example.app3do.models.product.DataProduct;
import com.google.gson.annotations.SerializedName;

public class DataCart {

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("product")
    private DataProduct product;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DataProduct getProduct() {
        return product;
    }

    public void setProduct(DataProduct product) {
        this.product = product;
    }
}

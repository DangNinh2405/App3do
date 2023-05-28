package com.example.app3do.models.cart;

import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("product_id")
    private int productId;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("is_add_more")
    private boolean isAddMore;


    public int getProductId() {
        return productId;
    }

    public Cart(int productId, int quantity, boolean isAddMore) {
        this.productId = productId;
        this.quantity = quantity;
        this.isAddMore = isAddMore;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAddMore() {
        return isAddMore;
    }

    public void setAddMore(boolean addMore) {
        isAddMore = addMore;
    }

}

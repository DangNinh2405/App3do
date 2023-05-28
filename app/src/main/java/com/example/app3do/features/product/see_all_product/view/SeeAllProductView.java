package com.example.app3do.features.product.see_all_product.view;

import com.example.app3do.models.product.DataProduct;

import java.util.List;

public interface SeeAllProductView {
    void sendMessage(String message, boolean isUpdate);
    void createAllProduct(List<DataProduct> list);

    void isLoading(boolean isLoading);
}

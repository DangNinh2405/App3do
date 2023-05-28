package com.example.app3do.features.product.search_product.view;

import com.example.app3do.models.product.DataProduct;

import java.util.List;

public interface SearchProductView {

    void isLoading(boolean isLoading);
    void createSearchProduct(List<DataProduct> list);
    void sendMessage(String message, boolean isUpdate);
}

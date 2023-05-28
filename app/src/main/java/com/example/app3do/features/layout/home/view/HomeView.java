package com.example.app3do.features.layout.home.view;

import com.example.app3do.models.home.DataCategory;
import com.example.app3do.models.home.DataSlide;
import com.example.app3do.models.product.DataProduct;

import java.util.List;

public interface HomeView {
    void createSlider(List<DataSlide> list);
    void getMessage(String message);

    void createHotSaleOne(List<DataProduct> list);
    void createHotSaleTwo(List<DataProduct> list);

    void createNewProduct(List<DataProduct> list);

    void createCategory(List<DataCategory> list);

    void createQuantityCart(int quantity);
}

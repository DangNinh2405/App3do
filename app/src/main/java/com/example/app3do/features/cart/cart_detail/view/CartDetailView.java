package com.example.app3do.features.cart.cart_detail.view;

import com.example.app3do.models.cart.BodyCart;
import com.example.app3do.models.personal.BodyPersonal;

public interface CartDetailView {
    void sendMessage(String message);
    void createViewProductCart(BodyCart bodyCart, boolean isUpdate);

    void createViewAddressCart(BodyPersonal bodyPersonal);
}

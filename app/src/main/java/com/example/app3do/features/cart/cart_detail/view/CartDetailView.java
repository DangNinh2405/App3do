package com.example.app3do.features.cart.cart_detail.view;

import com.example.app3do.models.cart.BodyCart;

public interface CartDetailView {
    void sendMessage(String message);
    void createViewCart(BodyCart bodyCart);
}

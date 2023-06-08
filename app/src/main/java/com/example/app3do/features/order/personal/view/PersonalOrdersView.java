package com.example.app3do.features.order.personal.view;

import com.example.app3do.models.order.DataOrder;

import java.util.List;

public interface PersonalOrdersView {
    void sendMessage(String message, boolean sendBroadcast);
    void createOrdersView(List<DataOrder> order);
    void isLoading(boolean isLoading);
    void openCart();
}

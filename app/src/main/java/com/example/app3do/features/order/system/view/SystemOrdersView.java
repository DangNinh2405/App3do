package com.example.app3do.features.order.system.view;

import com.example.app3do.models.order.DataOrder;

import java.util.List;

public interface SystemOrdersView {
    void sendMessage(String message, boolean sendBroadcast);
    void createOrdersView(List<DataOrder> order);
    void isLoading(boolean isLoading);
    void openCart();
}

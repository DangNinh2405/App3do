package com.example.app3do.features.notification.view;

import com.example.app3do.models.notification.DataNotification;

import java.util.List;

public interface NotificationView {
    void sendMessage(String message);
    void loading(boolean isLoading);
    void createNotificationView(List<DataNotification> data);
}

package com.example.app3do.features.account.login.view;

import com.example.app3do.models.account.DataLogin;

public interface LoginView {
    void sendMessage(String message);
    void LoginSuccess(DataLogin dataLogin);
}

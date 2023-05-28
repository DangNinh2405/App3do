package com.example.app3do.features.account.verify.view;

public interface VerifyView {
    void sendMessage(String message);
    void sendUserId(int userId);
    void showDialogSuccess();
}

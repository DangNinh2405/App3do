package com.example.app3do.features.personal_information.banks_account.view;

import com.example.app3do.models.personal.BanksPersonal;

import java.util.List;

public interface BanksAccountView {
    void sendMessage(String message);
    void createBanksAccount(List<BanksPersonal> list);
    void loading(boolean isLoading);
}

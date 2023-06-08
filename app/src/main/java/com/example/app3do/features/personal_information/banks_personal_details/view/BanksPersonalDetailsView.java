package com.example.app3do.features.personal_information.banks_personal_details.view;

import com.example.app3do.models.personal.DataPersonal;

public interface BanksPersonalDetailsView {
    void sendMessage(String message);
    void renderView(DataPersonal personal);
}

package com.example.app3do.features.personal_information.personal_details.view;

import com.example.app3do.models.personal.DataPersonal;

public interface PersonalDetailsView {
    void sendMessage(String message);
    void showData(DataPersonal personal);
}

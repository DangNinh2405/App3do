package com.example.app3do.features.layout.personal.view;

import com.example.app3do.models.personal.DataPersonal;

public interface PersonalView {
    void sendMessage(String message);
    void renderPersonalInformation(DataPersonal personal);
}

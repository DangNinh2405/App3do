package com.example.app3do.features.personal_information.change_information.view;

import com.example.app3do.models.personal.DataPersonal;
import com.example.app3do.models.personal.DataUploadImage;

public interface ChangeInformationView {
    void sendMessage(String message, boolean isSuccess);
    void showImage(DataUploadImage image, int select);
    void loading(boolean isLoading, int select);
    void createProfile(DataPersonal data);
}

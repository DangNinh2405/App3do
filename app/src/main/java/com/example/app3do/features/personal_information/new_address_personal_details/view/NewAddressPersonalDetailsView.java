package com.example.app3do.features.personal_information.new_address_personal_details.view;

import android.widget.TextView;

import com.example.app3do.models.personal.WardDistrictProvincePersonal;

import java.util.List;

public interface NewAddressPersonalDetailsView {
    void sendMessage(String message);
    void sendBroadcast();

    void renderAddress(List<WardDistrictProvincePersonal> list, String action, TextView view);
}

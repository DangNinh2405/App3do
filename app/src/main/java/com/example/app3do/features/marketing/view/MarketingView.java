package com.example.app3do.features.marketing.view;

import com.example.app3do.models.marketing.BodyMarketing;
import com.example.app3do.models.marketing.MetaMarketing;
import com.example.app3do.models.personal.DataPersonal;

import java.util.List;

public interface MarketingView {
    void sendMessage(String message);
    void createMemberMarketingView(MetaMarketing meta, List<DataPersonal> dataList);

    void loading(boolean isLoading, boolean rcvIsGone);
}

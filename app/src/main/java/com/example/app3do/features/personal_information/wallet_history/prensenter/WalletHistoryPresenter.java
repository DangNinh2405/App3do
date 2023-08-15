package com.example.app3do.features.personal_information.wallet_history.prensenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.personal_information.wallet_history.view.WalletHistoryView;
import com.google.gson.JsonElement;

public class WalletHistoryPresenter extends BasePresenterT<WalletHistoryView> {
    private final int size = 10;
    private final String type = "moneyadd";
    private int page;
    private int totalPage;

    public WalletHistoryPresenter(WalletHistoryView view) {
        super(view);
        init();
    }

    private void init() {
        this.page = 1;
        this.totalPage = 0;
    }

    public void getMoneyLogs(String accessToken){
        if (totalPage != 0 && totalPage < page) {
            return;
        }

        if (!isViewAttached()) {
            return;
        }

        getView().loading(true);

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.getMoneyLogs(accessToken, page, type, size)) {
            @Override
            public void isSuccess(JsonElement obj) {
                getView().createWalletHistory();
                getView().loading(false);
            }

            @Override
            public void isFailed(String error) {
                getView().sendMessage(error);
            }
        };

        response.callAPI();
    }
}

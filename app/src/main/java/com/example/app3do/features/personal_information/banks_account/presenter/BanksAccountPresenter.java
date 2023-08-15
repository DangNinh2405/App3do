package com.example.app3do.features.personal_information.banks_account.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.personal_information.banks_account.view.BanksAccountView;
import com.example.app3do.models.personal.BanksPersonal;
import com.example.app3do.models.personal.BodyBanksAccount;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.List;

public class BanksAccountPresenter extends BasePresenterT<BanksAccountView> {
    public BanksAccountPresenter(BanksAccountView view) {
        super(view);
    }

    public void getBanksAccount(String accessToken){
        if (!isViewAttached()) {
            return;
        }

        getView().loading(true);

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.getBanksAccount(accessToken)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (obj.getAsJsonObject().get("data").isJsonArray()) {
                    Gson gson = new Gson();
                    BodyBanksAccount body = gson.fromJson(obj, BodyBanksAccount.class);
                    getView().createBanksAccount(body.getData());
                    getView().loading(false);
                }
            }

            @Override
            public void isFailed(String error) {
                getView().sendMessage(error);
            }
        };

        response.callAPI();
    }
}

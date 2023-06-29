package com.example.app3do.features.personal_information.banks_personal_details.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.personal_information.banks_personal_details.view.BanksPersonalDetailsView;
import com.example.app3do.models.personal.BodyPersonal;
import com.example.app3do.models.personal.DeleteAddress;
import com.google.gson.JsonElement;

public class BanksPersonalDetailsPresenter extends BasePresenterT<BanksPersonalDetailsView> {

    private final String WITH = "banks,wallet,addresses";
    public BanksPersonalDetailsPresenter(BanksPersonalDetailsView view) {
        super(view);
    }

    public void deleteBankPersonal(String accessToken, int id) {
        DeleteAddress address = new DeleteAddress(accessToken, id);

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.deleteBankById(id, accessToken, address)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (isViewAttached()) {
                    if (obj.getAsJsonObject().get("meta").isJsonArray()) {
                        getPersonalInformation(accessToken);
                        getView().sendMessage("Xóa tài khoản thành công.");
                    }
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().sendMessage(error);
                }
            }
        };

        response.callAPI();
    }

    public void getPersonalInformation(String accessToken) {
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyPersonal> response = new HandleResponse<BodyPersonal>(apiService.getPersonalInformation(accessToken, WITH)) {
            @Override
            public void isSuccess(BodyPersonal obj) {
                if (isViewAttached()) {
                    getView().renderView(obj.getData());
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().sendMessage(error);
                }
            }
        };

        response.callAPI();
    }
}

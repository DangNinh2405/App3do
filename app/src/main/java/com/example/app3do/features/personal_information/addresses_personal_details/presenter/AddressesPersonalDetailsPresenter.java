package com.example.app3do.features.personal_information.addresses_personal_details.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.personal_information.addresses_personal_details.view.AddressesPersonalDetailsView;
import com.example.app3do.models.personal.BodyPersonal;
import com.example.app3do.models.personal.DeleteAddress;
import com.google.gson.JsonElement;

public class AddressesPersonalDetailsPresenter extends BasePresenterT<AddressesPersonalDetailsView> {
    private final String WITH = "banks,wallet,addresses";
    public AddressesPersonalDetailsPresenter(AddressesPersonalDetailsView view) {
        super(view);
    }

    public void deleteAddressPersonal(String accessToken, int id) {
        DeleteAddress address = new DeleteAddress(accessToken, id);

        APIService apiService = BaseAPIClient.getInstance().getAPIService();
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.deleteAddressById(id, accessToken, address)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (isViewAttached()) {
                    if (obj.getAsJsonObject().get("meta").isJsonArray()) {
                        getPersonalInformation(accessToken);
                        getView().sendMessage("Xóa địa chỉ thành công.");
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
        APIService apiService = BaseAPIClient.getInstance().getAPIService();
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

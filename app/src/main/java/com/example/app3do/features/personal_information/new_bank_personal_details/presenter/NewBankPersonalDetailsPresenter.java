package com.example.app3do.features.personal_information.new_bank_personal_details.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.personal_information.new_bank_personal_details.view.NewBankPersonalDetailsView;
import com.example.app3do.models.personal.NewbankPersonal;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class NewBankPersonalDetailsPresenter extends BasePresenterT<NewBankPersonalDetailsView> {
    public NewBankPersonalDetailsPresenter(NewBankPersonalDetailsView view) {
        super(view);
    }

    public void addNewBank(NewbankPersonal personal) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(personal));

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.addNewBank(personal.getAccessToken(), body)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (isViewAttached()) {
                    if (obj.getAsJsonObject().get("data").isJsonObject()) {
                        Gson gson = new Gson();
                        int id = gson.fromJson(obj.getAsJsonObject().get("data").getAsJsonObject().get("id"), Integer.class);

                        getView().sendMessage("Thêm tài khoản ngân hàng thành công.");
                        getView().sendBroadcast();
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
}

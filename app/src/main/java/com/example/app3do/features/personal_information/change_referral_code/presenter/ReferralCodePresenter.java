package com.example.app3do.features.personal_information.change_referral_code.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.personal_information.change_referral_code.view.ReferralCodeView;
import com.example.app3do.models.change_referral_code.ChangeReferralCode;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ReferralCodePresenter extends BasePresenterT<ReferralCodeView> {
    public ReferralCodePresenter(ReferralCodeView view) {
        super(view);
    }

    public void changeReferralCode(ChangeReferralCode code){

        Gson gson = new Gson();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(code));

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.updateReferralCode(code.getAccessToken(), body)) {
            @Override
            public void isSuccess(JsonElement obj) {
                String success = "Cập nhật mã giới thiệu thành công";
                getView().sendMessage(success, true);
            }

            @Override
            public void isFailed(String error) {
                getView().sendMessage(error, false);
            }
        };

        response.callAPI();
    }
}

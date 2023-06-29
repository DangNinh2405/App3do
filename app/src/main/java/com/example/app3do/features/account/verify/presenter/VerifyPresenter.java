package com.example.app3do.features.account.verify.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.account.verify.view.VerifyView;
import com.example.app3do.models.verify.BodyVerify;
import com.google.gson.JsonElement;

public class VerifyPresenter extends BasePresenterT<VerifyView> {
    public VerifyPresenter(VerifyView view) {
        super(view);
    }

    public void sendOTP(String phoneNumber){
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyVerify> response = new HandleResponse<BodyVerify>(apiService.reSendConfirmCode(phoneNumber)) {
            @Override
            public void isSuccess(BodyVerify obj) {
                if (isViewAttached()){
                    getView().sendUserId(obj.getData().getId());
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()){
                    getView().sendMessage(error);
                }
            }
        };

        response.callAPI();
    }

    public void checkConfirmCode(String smsCode1, String smsCode2, String smsCode3, String smsCode4 , int user_id){
        String smsCode = (smsCode1 + smsCode2 + smsCode3 + smsCode4).trim();
        checkSmsCode(smsCode, user_id);
    }

    private void checkSmsCode(String smsCode, int user_id){
        APIService api = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(api.checkConfirmCode(user_id, smsCode)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (isViewAttached()){
                    getView().showDialogSuccess();
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

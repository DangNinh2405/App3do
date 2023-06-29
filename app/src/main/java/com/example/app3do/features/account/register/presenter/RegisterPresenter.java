package com.example.app3do.features.account.register.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.account.register.view.RegisterView;
import com.example.app3do.models.register.Register;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RegisterPresenter extends BasePresenterT<RegisterView> {
    public RegisterPresenter(RegisterView view) {
        super(view);
    }

    public void handleRegister(Register register){
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(register));

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<Register> response = new HandleResponse<Register>(apiService.register(body)) {
            @Override
            public void isSuccess(Register obj) {
                if (isViewAttached()) {
                    getView().sendVerify(register.getPhoneNumber());
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

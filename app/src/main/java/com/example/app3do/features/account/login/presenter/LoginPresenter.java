package com.example.app3do.features.account.login.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.account.login.view.LoginView;
import com.example.app3do.models.account.BodyLogin;
import com.example.app3do.models.account.Login;

public class LoginPresenter extends BasePresenterT<LoginView> {

    public LoginPresenter(LoginView view) {
        super(view);
    }

    public void handleLogin(Login login){
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyLogin> response = new HandleResponse<BodyLogin>(apiService.login(login.getUserName(), login.getPassword())) {
            @Override
            public void isSuccess(BodyLogin obj) {
                if (isViewAttached()) {
                    getView().LoginSuccess(obj.getDataLogin());
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

package com.example.app3do.features.account.login.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.app3do.R;
import com.example.app3do.base.BasePresenterT;
import com.example.app3do.constans.Constants;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.account.login.view.LoginView;
import com.example.app3do.models.account.BodyLogin;
import com.example.app3do.models.account.Login;
import com.google.gson.Gson;

public class LoginPresenter extends BasePresenterT<LoginView> {
    private Gson gson = new Gson();

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

    public void handlePutData(Context context, Login login) {
        SharedPreferences preferences = context.getSharedPreferences(String.valueOf(R.string.KEY_LOGIN_ACCOUNT), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String data = gson.toJson(login);
        editor.putString(Constants.ACCOUNT, data);
        editor.apply();
    }

    public Login getDataLogin(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(String.valueOf(R.string.KEY_LOGIN_ACCOUNT), Context.MODE_PRIVATE);
        String data = preferences.getString(Constants.ACCOUNT, "");

        if (!data.isEmpty()) {
            Login login = gson.fromJson(data, Login.class);
            return login;
        }

        return null;
    }
}

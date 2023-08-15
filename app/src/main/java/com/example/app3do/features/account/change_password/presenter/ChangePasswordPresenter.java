package com.example.app3do.features.account.change_password.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.account.change_password.view.ChangePasswordView;
import com.example.app3do.models.account.ChangePassword;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ChangePasswordPresenter extends BasePresenterT<ChangePasswordView> {
    private final String WITH = "banks,wallet,addresses";
    private final String SUCCESSFUL = "Đổi mật khẩu thành công";

    public ChangePasswordPresenter(ChangePasswordView view) {
        super(view);
    }

    public void updatePassword(ChangePassword password) {
        if (!isViewAttached()) {
            return;
        }

        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(password));
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.updatePassword(password.getAccessToken(), body)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (obj.getAsJsonObject().get("data").isJsonObject()) {
                    getView().sendMessage(SUCCESSFUL, true);
                }
            }

            @Override
            public void isFailed(String error) {
                getView().sendMessage(error, false);
            }
        };

        response.callAPI();
    }
}

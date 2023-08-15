package com.example.app3do.features.personal_information.exchange_points.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.personal_information.exchange_points.view.ExchangePointsView;
import com.example.app3do.models.exchange_points.ExchangePoints;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ExchangePointsPresenter extends BasePresenterT<ExchangePointsView> {
    public ExchangePointsPresenter(ExchangePointsView view) {
        super(view);
    }

    public void exchangePoints(ExchangePoints exchangePoints) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json ; charset=utf-8"), gson.toJson(exchangePoints));
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.exchangePoints(exchangePoints.getAccessToken(), body)) {
            @Override
            public void isSuccess(JsonElement obj) {
                getView().sendMessage("Đổi điểm tích luỹ thành công");
            }

            @Override
            public void isFailed(String error) {
                getView().sendMessage(error);
            }
        };

        response.callAPI();
    }
}

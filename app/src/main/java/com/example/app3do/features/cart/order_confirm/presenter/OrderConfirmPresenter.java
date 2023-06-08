package com.example.app3do.features.cart.order_confirm.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.cart.order_confirm.view.OrderConfirmView;
import com.example.app3do.models.cart.Order;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OrderConfirmPresenter extends BasePresenterT<OrderConfirmView> {
    private final String WITH = "products";
    public OrderConfirmPresenter(OrderConfirmView view) {
        super(view);
    }

    public void handleOrderPayment(Order order){
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(order));
        APIService apiService = BaseAPIClient.getInstance().getAPIService();
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.orderPayment(WITH, order.getAccessToken(), body)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (isViewAttached()) {
                    if (obj.getAsJsonObject().get("data").isJsonObject()) {
                        getView().sendBroadcastReceiver();
                        getView().sendMessage("Tạo đơn hàng thành công");
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

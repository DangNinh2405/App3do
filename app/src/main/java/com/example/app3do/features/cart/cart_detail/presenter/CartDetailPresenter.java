package com.example.app3do.features.cart.cart_detail.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.cart.cart_detail.view.CartDetailView;
import com.example.app3do.models.cart.BodyCart;
import com.example.app3do.models.cart.Cart;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CartDetailPresenter extends BasePresenterT<CartDetailView> {
    public CartDetailPresenter(CartDetailView view) {
        super(view);
    }

    public void getProductInCart(String accessToken){
        APIService apiService = BaseAPIClient.getInstance().getAPIService();
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.getListProductToCart(accessToken)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (!obj.getAsJsonObject().get("meta").isJsonArray()){
                    Gson gson = new Gson();
                    BodyCart bodyCart = gson.fromJson(obj, BodyCart.class);

                    if (bodyCart != null) {
                        if (isViewAttached()) {
                            getView().createViewCart(bodyCart);
                        }
                    }
                } else {
                    getView().createViewCart(null);
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

    public void updateProductInCart(String accessToken, Cart cart){
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(cart));
        APIService apiService = BaseAPIClient.getInstance().getAPIService();
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.updateProductInCart(accessToken, body)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (!obj.getAsJsonObject().get("meta").isJsonArray()){
                    Gson gson = new Gson();
                    BodyCart bodyCart = gson.fromJson(obj, BodyCart.class);

                    if (bodyCart != null) {
                        if (isViewAttached()) {
                            getView().createViewCart(bodyCart);
                        }
                    }
                } else {
                    getView().createViewCart(null);
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

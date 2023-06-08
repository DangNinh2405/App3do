package com.example.app3do.features.cart.cart_detail.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.cart.cart_detail.view.CartDetailView;
import com.example.app3do.features.layout.personal.view.PersonalView;
import com.example.app3do.models.cart.BodyCart;
import com.example.app3do.models.cart.Cart;
import com.example.app3do.models.personal.BodyPersonal;
import com.example.app3do.models.personal.DeleteAddress;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CartDetailPresenter extends BasePresenterT<CartDetailView> {
    private final String WITH = "banks,wallet,addresses";
    public CartDetailPresenter(CartDetailView view) {
        super(view);
    }

    public void getProductInCart(String accessToken, boolean isUpdate){
        APIService apiService = BaseAPIClient.getInstance().getAPIService();
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.getListProductToCart(accessToken)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (!obj.getAsJsonObject().get("meta").isJsonArray()){
                    Gson gson = new Gson();
                    BodyCart bodyCart = gson.fromJson(obj, BodyCart.class);

                    if (bodyCart != null) {
                        if (isViewAttached()) {
                            getView().createViewProductCart(bodyCart, isUpdate);
                        }
                    }
                } else {
                    getView().createViewProductCart(null, isUpdate);
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

    public void updateProductInCart(String accessToken, Cart cart, boolean isUpdate){
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
                            getView().createViewProductCart(bodyCart, isUpdate);
                        }
                    }
                } else {
                    getView().createViewProductCart(null, isUpdate);
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


    public void getPersonalInformation(String accessToken) {
        APIService apiService = BaseAPIClient.getInstance().getAPIService();
        HandleResponse<BodyPersonal> response = new HandleResponse<BodyPersonal>(apiService.getPersonalInformation(accessToken, WITH)) {
            @Override
            public void isSuccess(BodyPersonal obj) {
                if (isViewAttached()) {
                    getView().createViewAddressCart(obj);
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

    public void deleteAddressPersonal(String accessToken, int id) {
        DeleteAddress address = new DeleteAddress(accessToken, id);

        APIService apiService = BaseAPIClient.getInstance().getAPIService();
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.deleteAddressById(id, accessToken, address)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (isViewAttached()) {
                    if (obj.getAsJsonObject().get("meta").isJsonArray()) {
                        getPersonalInformation(accessToken);
                        getView().sendMessage("Xóa địa chỉ thành công.");
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

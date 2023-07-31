package com.example.app3do.features.product.product_detail.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.product.product_detail.view.ProductDetailView;
import com.example.app3do.models.cart.BodyCart;
import com.example.app3do.models.cart.Cart;
import com.example.app3do.models.notification.BodyNotification;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ProductDetailPresenter extends BasePresenterT<ProductDetailView> {
    private final int PAGE = 1;
    public ProductDetailPresenter(ProductDetailView view) {
        super(view);
    }

    public void createQuantityCart(String accessToken){
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.getListProductToCart(accessToken)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (!obj.getAsJsonObject().get("meta").isJsonArray()) {
                    Gson gson = new Gson();
                    BodyCart bodyCart = gson.fromJson(obj, BodyCart.class);

                    if (bodyCart != null) {
                        if (isViewAttached()) {
                            getView().createQuantityCart(bodyCart.getMeTaCart().getTotalProduct());
                        }
                    }
                } else {
                    if (isViewAttached()) {
                        getView().createQuantityCart(0);
                    }
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

    public void updateProductInCart(String accessToken, Cart cart, boolean isBuyNow) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(cart));
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.updateProductInCart(accessToken, body)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (isViewAttached()) {
                    if (!obj.getAsJsonObject().get("meta").isJsonArray()) {
                        Gson gson = new Gson();
                        BodyCart bodyCart = gson.fromJson(obj, BodyCart.class);

                        if (bodyCart != null) {
                            if (isBuyNow) {
                                getView().startCart();
                            } else {
                                getView().sendMessage("Thêm sản phẩm vào giỏ thành công");
                            }
                        }
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

    public void createQuantifyNotification(String accessToken) {
        if (!isViewAttached()) {
            return;
        }

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyNotification> response = new HandleResponse<BodyNotification>(apiService.getNotifications(accessToken, PAGE)) {
            @Override
            public void isSuccess(BodyNotification obj) {
                getView().createQuantityNotification(obj.getMeta().getUnread());
            }

            @Override
            public void isFailed(String error) {
                getView().sendMessage(error);
            }
        };

        response.callAPI();
    }
}

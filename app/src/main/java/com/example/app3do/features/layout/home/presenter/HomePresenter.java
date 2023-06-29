package com.example.app3do.features.layout.home.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.layout.home.view.HomeView;
import com.example.app3do.models.cart.BodyCart;
import com.example.app3do.models.home.BodyCategory;
import com.example.app3do.models.home.BodySlide;
import com.example.app3do.models.notification.BodyNotification;
import com.example.app3do.models.product.BodyProduct;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class HomePresenter extends BasePresenterT<HomeView> {
    private final String SORT_BY = "created_at";
    private final int PAGE = 1;
    private final int IS_HOT = 1;
    public HomePresenter(HomeView view) {
        super(view);
    }

    public void createSlider(){
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodySlide> response = new HandleResponse<BodySlide>(apiService.getListSlider()) {
            @Override
            public void isSuccess(BodySlide obj) {
                if (isViewAttached()) {
                    getView().createSlider(obj.getDataSlide());
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().getMessage(error);
                }
            }
        };

        response.callAPI();
    }

    public void createHotSaleProduct(int page, boolean hotOne){
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyProduct> response = new HandleResponse<BodyProduct>(apiService.getListHotProduct(IS_HOT, page)) {
            @Override
            public void isSuccess(BodyProduct obj) {
                if (isViewAttached()) {
                    if (hotOne) {
                        getView().createHotSaleOne(obj.getDataProduct());
                    } else {
                        getView().createHotSaleTwo(obj.getDataProduct());
                    }
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().getMessage(error);
                }
            }
        };

        response.callAPI();
    }

    public void createNewProduct(int page){
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyProduct> response = new HandleResponse<BodyProduct>(apiService.getListNewProduct(SORT_BY, page)) {
            @Override
            public void isSuccess(BodyProduct obj) {
                if (isViewAttached()) {
                    getView().createNewProduct(obj.getDataProduct());
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().getMessage(error);
                }
            }
        };

        response.callAPI();
    }

    public void createCategory(){
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyCategory> response = new HandleResponse<BodyCategory>(apiService.getListCategory()) {
            @Override
            public void isSuccess(BodyCategory obj) {
                if (isViewAttached()) {
                    getView().createCategory(obj.getDataCategory());
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().getMessage(error);
                }
            }
        };

        response.callAPI();
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
                    getView().getMessage(error);
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
                getView().createQuantifyNotification(obj.getMeta().getUnread());
            }

            @Override
            public void isFailed(String error) {
                getView().getMessage(error);
            }
        };

        response.callAPI();
    }
}

package com.example.app3do.features.product.search_product.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.product.search_product.view.SearchProductView;
import com.example.app3do.models.cart.BodyCart;
import com.example.app3do.models.cart.Cart;
import com.example.app3do.models.product.BodyProduct;
import com.example.app3do.models.product.DataProduct;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SearchProductPresenter extends BasePresenterT<SearchProductView> {
    private int page;
    private int totalPage;
    private List<DataProduct> list;

    public void init(){
        this.page = 1;
        this.totalPage = 0;
        this.list = new ArrayList<>();
    }

    public SearchProductPresenter(SearchProductView view) {
        super(view);
        init();
    }

    public void getSearchProductPage(String keyword){
        if (totalPage != 0 && totalPage < page) {
            return;
        }

        if (isViewAttached()) {
            getView().isLoading(true);
        }

        APIService apiService = BaseAPIClient.getInstance().getAPIService();
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.getProductByKeyword(keyword, page)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (!obj.getAsJsonObject().get("meta").isJsonArray()) {
                    Gson gson = new Gson();
                    BodyProduct product = gson.fromJson(obj, BodyProduct.class);
                    if (isViewAttached()) {
                        totalPage = product.getMetaProduct().getPagination().getTotal_pages();
                        if (page <= totalPage) {
                            if (list == null) {
                                list = new ArrayList<>();
                            }

                            list.addAll(product.getDataProduct());
                            getView().createSearchProduct(list);
                            page++;
                            getView().isLoading(false);
                        }
                    }
                } else {
                    if (isViewAttached()) {
                        getView().createSearchProduct(new ArrayList<>());
                        getView().isLoading(false);
                    }

                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().sendMessage(error, false);
                }
            }
        };

        response.callAPI();
    }

    public void updateProductInCart(String accessToken, Cart cart) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(cart));
        APIService apiService = BaseAPIClient.getInstance().getAPIService();
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.updateProductInCart(accessToken, body)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (isViewAttached()) {
                    if (!obj.getAsJsonObject().get("meta").isJsonArray()) {
                        Gson gson = new Gson();
                        BodyCart bodyCart = gson.fromJson(obj, BodyCart.class);

                        if (bodyCart != null) {
                            getView().sendMessage("Thêm sản phẩm vào giỏ thành công", true);
                        }
                    }
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().sendMessage(error, false);
                }
            }
        };

        response.callAPI();
    }
}

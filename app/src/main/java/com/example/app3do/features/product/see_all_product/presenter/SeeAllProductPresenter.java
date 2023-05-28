package com.example.app3do.features.product.see_all_product.presenter;

import android.os.Handler;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.product.see_all_product.view.SeeAllProductView;
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
import retrofit2.Call;

public class SeeAllProductPresenter extends BasePresenterT<SeeAllProductView> {
    private final String sort_by = "created_at";
    private final int is_hot = 1;
    private APIService apiService = BaseAPIClient.getInstance().getAPIService();
    private int page;
    private int totalPage;
    private List<DataProduct> list;

    public SeeAllProductPresenter(SeeAllProductView view) {
        super(view);
        this.page = 1;
        this.totalPage = 0;
        this.list = new ArrayList<>();
    }

    public void getHotProductPage() {
        handleCallAPI(apiService.getListHotProduct(is_hot, page));
    }

    public void getNewProductPage() {
        handleCallAPI(apiService.getListNewProduct(sort_by, page));
    }

    public void getCategoryProductPage(int productId) {
        handleCallAPI(apiService.getListProductByCategoryId(productId, page));
    }

    public void handleCallAPI(Call api){
        if (totalPage != 0 && totalPage < page) {
            return;
        }

        if (isViewAttached()) {
            getView().isLoading(true);
        }

        HandleResponse<BodyProduct> response = new HandleResponse<BodyProduct>(api) {
            @Override
            public void isSuccess(BodyProduct obj) {
                if (isViewAttached()) {
                    totalPage = obj.getMetaProduct().getPagination().getTotal_pages();
                    if (page <= totalPage) {
                        if (list == null) {
                            list = new ArrayList<>();
                        }

                        list.addAll(obj.getDataProduct());
                        getView().createAllProduct(list);
                        page++;
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
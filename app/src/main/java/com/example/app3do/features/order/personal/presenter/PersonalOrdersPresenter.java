package com.example.app3do.features.order.personal.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.order.personal.view.PersonalOrdersView;
import com.example.app3do.models.order.BodyOrder;
import com.example.app3do.models.order.DataOrder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PersonalOrdersPresenter extends BasePresenterT<PersonalOrdersView> {
    private final String WITH = "products";
    private int page;
    private int totalPage;
    private List<DataOrder> list;
    public PersonalOrdersPresenter(PersonalOrdersView view) {
        super(view);
        init();
    }

    public void init(){
        this.page = 1;
        this.totalPage = 0;
        this.list = new ArrayList<>();
    }

    public void createOrder(String accessToken, String startDate, String endDate){
        if (totalPage != 0 && totalPage < page) {
            return;
        }

        if (isViewAttached()) {
            getView().isLoading(true);
        }

        APIService apiService = BaseAPIClient.getInstance().getAPIService();
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.getOrders(WITH, accessToken, page, startDate, endDate)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (isViewAttached()) {
                    if (obj.getAsJsonObject().get("meta").isJsonObject()) {
                        Gson gson = new Gson();
                        BodyOrder order = gson.fromJson(obj, BodyOrder.class);

                        totalPage = order.getMeta().getPagination().getTotal_pages();
                        if (page <= totalPage) {
                            if (list == null) {
                                list = new ArrayList<>();
                            }

                            list.addAll(order.getData());
                            getView().createOrdersView(list);
                            page++;
                            getView().isLoading(false);
                        }
                    } else {
                        getView().createOrdersView(new ArrayList<>());
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

    public void cancelOrder(int id, String accessToken){
        APIService apiService = BaseAPIClient.getInstance().getAPIService();
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.deleteOrder(id, accessToken)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (isViewAttached()) {
                    getView().sendMessage("Hủy đơn hàng thành công", true);
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

    public void orderAgain(int id, String accessToken, DataOrder order){
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(order));

        APIService apiService = BaseAPIClient.getInstance().getAPIService();
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.putOrder(id, accessToken, body)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (isViewAttached()) {
                    getView().sendMessage("Hủy đơn hàng thành công", true);
                    getView().openCart();
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

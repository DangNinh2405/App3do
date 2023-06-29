package com.example.app3do.features.notification.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.notification.view.NotificationView;
import com.example.app3do.models.notification.BodyNotification;
import com.example.app3do.models.notification.DataNotification;
import com.example.app3do.models.product.DataProduct;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class NotificationPresenter extends BasePresenterT<NotificationView> {
    private final int READ = 1;
    private int page;
    private int totalPage;
    private List<DataNotification> list;

    private void init() {
        this.page = 1;
        this.totalPage = 0;
        this.list = new ArrayList<>();
    }

    public NotificationPresenter(NotificationView view) {
        super(view);
        init();
    }

    public void createNotificationView(String accessToken) {
        if (totalPage != 0 && totalPage < page) {
            return;
        }

        if (!isViewAttached()) {
            return;
        }

        getView().loading(true);

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyNotification> response = new HandleResponse<BodyNotification>(apiService.readPageNotification(accessToken, page, READ)) {
            @Override
            public void isSuccess(BodyNotification obj) {

                totalPage = obj.getMeta().getPagination().getTotal_pages();
                if (page <= totalPage) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }

                    list.addAll(obj.getData());
                    page++;
                    getView().createNotificationView(list);
                    getView().loading(false);
                }
            }

            @Override
            public void isFailed(String error) {
                getView().sendMessage(error);
            }
        };

        response.callAPI();

    }
}

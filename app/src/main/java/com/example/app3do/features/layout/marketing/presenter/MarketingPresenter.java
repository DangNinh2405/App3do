package com.example.app3do.features.layout.marketing.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.layout.marketing.view.MarketingView;
import com.example.app3do.models.marketing.BodyMarketing;
import com.example.app3do.models.personal.DataPersonal;
import com.example.app3do.models.product.Pagination;
import com.example.app3do.models.report.PointReports;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MarketingPresenter extends BasePresenterT<MarketingView> {
    private Gson gson = new Gson();
    private int page;
    private List<DataPersonal> list;
    private int totalPage;
    private PointReports point;
    private Pagination pagination;

    public void init() {
        this.page = 1;
        this.totalPage = 0;
        this.list = new ArrayList<>();
    }

    public MarketingPresenter(MarketingView view) {
        super(view);
        init();
    }

    public void createMemberMarketingF1(String accessToken, String sort, String startDate, String endDate, boolean rcvIsGone) {
        if (isViewAttached()) {
            if (totalPage != 0 && totalPage < page) {
                return;
            }

            getView().loading(true, rcvIsGone);

            APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
            HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.getMemberMarketing(accessToken, page, sort, startDate, endDate)) {
                @Override
                public void isSuccess(JsonElement obj) {
                    if (obj.getAsJsonObject().get("meta").getAsJsonObject().get("point").isJsonArray()) {
                        point = null;
                    } else {
                        point = gson.fromJson(obj.getAsJsonObject().get("meta").getAsJsonObject().get("point"), PointReports.class);
                    }

                    Type dataListType = new TypeToken<List<DataPersonal>>() {}.getType();
                    List<DataPersonal> data = gson.fromJson(obj.getAsJsonObject().get("data"), dataListType);
                    pagination = gson.fromJson(obj.getAsJsonObject().get("meta").getAsJsonObject().get("pagination"), Pagination.class);

                    totalPage = pagination.getTotal_pages();
                    if (page <= totalPage) {
                        if (list == null) {
                            list = new ArrayList<>();
                        }

                        list.addAll(data);
                        getView().createMemberMarketingView(point, list);
                        page++;
                        getView().loading(false, rcvIsGone);
                    }
                }

                @Override
                public void isFailed(String error) {
                    getView().sendMessage(error);
                    getView().loading(false, rcvIsGone);
                }
            };

            response.callAPI();
        }

    }
}

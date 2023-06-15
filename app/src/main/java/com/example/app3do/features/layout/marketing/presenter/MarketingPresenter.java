package com.example.app3do.features.layout.marketing.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.layout.marketing.view.MarketingView;
import com.example.app3do.models.marketing.BodyMarketing;
import com.example.app3do.models.personal.DataPersonal;

import java.util.ArrayList;
import java.util.List;

public class MarketingPresenter extends BasePresenterT<MarketingView> {
    private int page;
    private List<DataPersonal> list;
    private int totalPage;

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

            APIService apiService = BaseAPIClient.getInstance().getAPIService();
            HandleResponse<BodyMarketing> response = new HandleResponse<BodyMarketing>(apiService.getMemberMarketing(accessToken, page, sort, startDate, endDate)) {
                @Override
                public void isSuccess(BodyMarketing obj) {
                    totalPage = obj.getMeta().getPagination().getTotal_pages();
                    if (page <= totalPage) {
                        if (list == null) {
                            list = new ArrayList<>();
                        }

                        list.addAll(obj.getData());
                        getView().createMemberMarketingView(obj.getMeta(), list);
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

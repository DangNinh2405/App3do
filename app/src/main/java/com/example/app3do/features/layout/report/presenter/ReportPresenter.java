package com.example.app3do.features.layout.report.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.layout.report.view.ReportView;
import com.example.app3do.models.report.BodyReports;

public class ReportPresenter extends BasePresenterT<ReportView> {
    public ReportPresenter(ReportView view) {
        super(view);
    }

    public void getReports(String accessToken, String startDate, String endDate) {

        if (isViewAttached()) {
            getView().loading(true);

            APIService apiService = BaseAPIClient.getInstance().getAPIService();
            HandleResponse<BodyReports> response = new HandleResponse<BodyReports>(apiService.getReports(accessToken, startDate, endDate)) {
                @Override
                public void isSuccess(BodyReports obj) {
                    getView().createViewReports(obj.getData());
                    getView().loading(false);
                }

                @Override
                public void isFailed(String error) {
                    getView().sendMessage(error);
                    getView().loading(false);
                }
            };

            response.callAPI();
        }

    }
}

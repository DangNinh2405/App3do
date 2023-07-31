package com.example.app3do.features.layout.report.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.layout.report.view.ReportView;
import com.example.app3do.models.report.BodyReports;
import com.example.app3do.models.report.CommissionReports;
import com.example.app3do.models.report.OrderReports;
import com.example.app3do.models.report.PointReports;
import com.example.app3do.models.report.UsersReports;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class ReportPresenter extends BasePresenterT<ReportView> {
    private Gson gson = new Gson();
    private OrderReports order;
    private CommissionReports commission;
    private PointReports point;
    private UsersReports users;
    public ReportPresenter(ReportView view) {
        super(view);
    }

    public void getReports(String accessToken, String startDate, String endDate) {

        if (isViewAttached()) {
            getView().loading(true);

            APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
            HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.getReports(accessToken, startDate, endDate)) {
                @Override
                public void isSuccess(JsonElement obj) {
                    order = gson.fromJson(obj.getAsJsonObject().get("data").getAsJsonObject().get("order"), OrderReports.class);
                    commission = gson.fromJson(obj.getAsJsonObject().get("data").getAsJsonObject().get("order"), CommissionReports.class);
                    users = gson.fromJson(obj.getAsJsonObject().get("data").getAsJsonObject().get("order"), UsersReports.class);

                    if (obj.getAsJsonObject().get("data").getAsJsonObject().get("point").isJsonObject()) {
                        point = gson.fromJson(obj.getAsJsonObject().get("data").getAsJsonObject().get("order"), PointReports.class);
                    } else {
                        point = null;
                    }

                    getView().createViewReports(order, commission, point, users);
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

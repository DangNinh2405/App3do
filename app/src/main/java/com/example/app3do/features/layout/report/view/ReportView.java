package com.example.app3do.features.layout.report.view;

import com.example.app3do.models.report.CommissionReports;
import com.example.app3do.models.report.DataReports;
import com.example.app3do.models.report.OrderReports;
import com.example.app3do.models.report.PointReports;
import com.example.app3do.models.report.UsersReports;

public interface ReportView {
    void sendMessage(String message);
    void createViewReports(OrderReports order, CommissionReports commission, PointReports point, UsersReports users);
    void loading(boolean isLoading);
}

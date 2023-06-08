package com.example.app3do.features.layout.report.view;

import com.example.app3do.models.report.DataReports;

public interface ReportView {
    void sendMessage(String message);
    void createViewReports(DataReports reports);

    void loading(boolean isLoading);
}

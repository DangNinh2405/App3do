package com.example.app3do.features.layout.report.fragment;

import android.view.View;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.layout.report.view.ReportView;

public class ReportFragment extends BaseFragment implements ReportView {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_report;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view ) {

    }

    private void initView() {

    }

    private void event() {

    }
}

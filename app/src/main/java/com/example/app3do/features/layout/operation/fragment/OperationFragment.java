package com.example.app3do.features.layout.operation.fragment;

import android.view.View;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.layout.operation.view.OperationView;

public class OperationFragment extends BaseFragment implements OperationView {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_operation;
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

package com.example.app3do.features.layout.system.fragment;

import android.view.View;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.layout.system.view.SystemView;

public class SystemFragment extends BaseFragment implements SystemView {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_system;
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

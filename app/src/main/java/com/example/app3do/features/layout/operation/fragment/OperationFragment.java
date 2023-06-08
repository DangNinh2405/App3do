package com.example.app3do.features.layout.operation.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.custom.TablayoutOperationAdapter;
import com.example.app3do.features.layout.operation.view.OperationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OperationFragment extends BaseFragment implements OperationView {
    TabLayout tl_operation;
    ViewPager2 vp_operation;

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

    private void init(View view) {
        tl_operation = view.findViewById(R.id.tl_operation);
        vp_operation = view.findViewById(R.id.vp_operation);
    }

    private void initView() {
        TablayoutOperationAdapter adapter = new TablayoutOperationAdapter(getActivity());
        vp_operation.setAdapter(adapter);

        new TabLayoutMediator(tl_operation, vp_operation, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Cá nhân");
                    break;
                case 1:
                    tab.setText("Hệ thống");
                    break;
                default:
                    tab.setText("Cá nhân");
                    break;
            }
        }).attach();
    }

    private void event() {

    }
}

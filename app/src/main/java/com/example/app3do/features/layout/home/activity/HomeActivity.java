package com.example.app3do.features.layout.home.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app3do.R;
import com.example.app3do.base.BaseActivity;
import com.example.app3do.constans.Constants;
import com.example.app3do.adapter.BottomNavigationHomeAdapter;
import com.example.app3do.models.account.DataLogin;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends BaseActivity {
    ViewPager2 view_pager_home;
    BottomNavigationView bottom_navigation_home;
    private String accessToken;
    @Override
    public int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        init();
        initView();
        event();
    }

    private void init() {
        view_pager_home = findViewById(R.id.view_pager_home);
        bottom_navigation_home = findViewById(R.id.bottom_navigation_home);

        Bundle bundle = getIntent().getExtras();
        DataLogin dataLogin = (DataLogin) bundle.getSerializable(Constants.USER);
        accessToken = dataLogin.getAccessToken();
    }

    private void initView() {
        BottomNavigationHomeAdapter homeAdapter = new BottomNavigationHomeAdapter(this);
        view_pager_home.setAdapter(homeAdapter);
        view_pager_home.setUserInputEnabled(false);
        view_pager_home.setOffscreenPageLimit(4);
    }

    private void event() {
        bottom_navigation_home.setOnItemSelectedListener( item -> {
            if (item.getItemId() == R.id.menu_home) {
                view_pager_home.setCurrentItem(0);
            } else if (item.getItemId() == R.id.menu_system) {
                view_pager_home.setCurrentItem(1);
            } else if (item.getItemId() == R.id.menu_operation) {
                view_pager_home.setCurrentItem(2);
            } else if (item.getItemId() == R.id.menu_report) {
                view_pager_home.setCurrentItem(3);
            } else if (item.getItemId() == R.id.menu_personal) {
                view_pager_home.setCurrentItem(4);
            } else {
                view_pager_home.setCurrentItem(0);
            }
            return true;
        });
    }

    public String getAccessToken(){
        return this.accessToken;
    }
}

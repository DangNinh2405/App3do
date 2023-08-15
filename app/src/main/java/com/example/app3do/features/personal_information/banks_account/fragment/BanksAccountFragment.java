package com.example.app3do.features.personal_information.banks_account.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.adapter.BanksAccountAdapter;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.personal_information.banks_account.presenter.BanksAccountPresenter;
import com.example.app3do.features.personal_information.banks_account.view.BanksAccountView;
import com.example.app3do.models.personal.BanksPersonal;

import java.util.List;

public class BanksAccountFragment extends BaseFragment implements BanksAccountView {

    BanksAccountPresenter presenter;
    HomeActivity homeActivity;
    ImageView img_back;
    RecyclerView rcv_banks_account;
    BanksAccountAdapter adapter;
    ProgressBar pg_loading;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_banks_account;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view){
        presenter = new BanksAccountPresenter(this);
        homeActivity = (HomeActivity) getActivity();
        img_back = view.findViewById(R.id.img_back);
        rcv_banks_account = view.findViewById(R.id.rcv_banks_account);
        pg_loading = view.findViewById(R.id.pg_loading);
    }

    private void initView() {
        presenter.getBanksAccount(homeActivity.getAccessToken());
    }

    private void event() {
        img_back.setOnClickListener( v -> {
            getParentFragmentManager().popBackStack();
        });

    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(homeActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createBanksAccount(List<BanksPersonal> list) {
        if (list != null && !list.isEmpty()) {
            adapter = new BanksAccountAdapter(list);
            rcv_banks_account.setAdapter(adapter);
            rcv_banks_account.setLayoutManager(new LinearLayoutManager(getContext()));

            adapter.setItemOnClickListener(bank -> {
                Toast.makeText(homeActivity, bank.getAccountNumber(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            pg_loading.setVisibility(View.VISIBLE);
        } else {
            pg_loading.setVisibility(View.GONE);
        }

    }
}

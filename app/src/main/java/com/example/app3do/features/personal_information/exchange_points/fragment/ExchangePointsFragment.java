package com.example.app3do.features.personal_information.exchange_points.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.personal_information.exchange_points.presenter.ExchangePointsPresenter;
import com.example.app3do.features.personal_information.exchange_points.view.ExchangePointsView;
import com.example.app3do.models.exchange_points.ExchangePoints;

public class ExchangePointsFragment extends BaseFragment implements ExchangePointsView {
    ImageView img_back;
    Button btn_exchange;
    EditText etxt_points;
    ExchangePointsPresenter presenter;
    HomeActivity homeActivity;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_exchange_points;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view ) {
        presenter = new ExchangePointsPresenter(this);
        homeActivity = (HomeActivity) getActivity();
        img_back = view.findViewById(R.id.img_back);
        etxt_points = view.findViewById(R.id.etxt_points);
        btn_exchange = view.findViewById(R.id.btn_exchange);
    }

    private void initView() {
        etxt_points.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etxt_points.length() > 0) {
                    btn_exchange.setVisibility(View.VISIBLE);
                } else {
                    btn_exchange.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_exchange.setOnClickListener( v -> {
            if (etxt_points.length() > 0) {
                int money = Integer.parseInt(etxt_points.getText().toString());
                ExchangePoints exchangePoints = new ExchangePoints(homeActivity.getAccessToken(), money);
                presenter.exchangePoints(exchangePoints);
            }
        });
    }

    private void event() {
        img_back.setOnClickListener( v -> {
            getParentFragmentManager().popBackStack();
        });
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}

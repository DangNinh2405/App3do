package com.example.app3do.features.personal_information.change_referral_code.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.personal_information.change_referral_code.presenter.ReferralCodePresenter;
import com.example.app3do.features.personal_information.change_referral_code.view.ReferralCodeView;
import com.example.app3do.models.change_referral_code.ChangeReferralCode;

public class ReferralCodeFragment extends BaseFragment implements ReferralCodeView {
    ImageView img_back;
    ReferralCodePresenter presenter;
    HomeActivity homeActivity;
    Button btn_update_referral_code;
    EditText etxt_referral_code;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_referral_code;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        presenter = new ReferralCodePresenter(this);
        homeActivity = (HomeActivity) getActivity();

        img_back = view.findViewById(R.id.img_back);
        btn_update_referral_code = view.findViewById(R.id.btn_update_referral_code);
        etxt_referral_code = view.findViewById(R.id.etxt_referral_code);
    }

    private void initView(){

    }

    private void event() {
        img_back.setOnClickListener( v -> {
            getParentFragmentManager().popBackStack();
        });

        btn_update_referral_code.setOnClickListener( v -> {
            String referralCode = etxt_referral_code.getText().toString();

            if (referralCode.isEmpty()) {
                String error = "Bạn chưa nhập mã giới thiệu mong muốn";
                sendMessage(error, false);
            } else {
                String accessToken = homeActivity.getAccessToken();

                ChangeReferralCode code = new ChangeReferralCode(referralCode, accessToken);
                presenter.changeReferralCode(code);
            }
        });
    }

    @Override
    public void sendMessage(String message, boolean isClose) {
        Toast.makeText(homeActivity, message, Toast.LENGTH_SHORT).show();

        if (isClose) {
            getParentFragmentManager().popBackStack();
        }
    }
}

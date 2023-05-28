package com.example.app3do.features.account.register.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.constans.Constants;
import com.example.app3do.features.account.register.presenter.RegisterPresenter;
import com.example.app3do.features.account.register.view.RegisterView;
import com.example.app3do.features.account.verify.fragment.VerifyFragment;
import com.example.app3do.models.register.Register;
import com.example.app3do.until.direction.Direction;

public class RegisterFragment extends BaseFragment implements RegisterView {
    RegisterPresenter presenter;
    ImageView img_back;
    Button btn_register;
    EditText etxt_referral_code, etxt_name, etxt_phone, etxt_password, etxt_re_password;
    LinearLayout lnl_register;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view ) {
        presenter = new RegisterPresenter(this);

        lnl_register = view.findViewById(R.id.lnl_register);
        img_back = view.findViewById(R.id.img_back);
        btn_register = view.findViewById(R.id.btn_register);
        etxt_referral_code = view.findViewById(R.id.etxt_referral_code);
        etxt_name = view.findViewById(R.id.etxt_name);
        etxt_phone = view.findViewById(R.id.etxt_phone);
        etxt_password = view.findViewById(R.id.etxt_password);
        etxt_re_password = view.findViewById(R.id.etxt_re_password);
    }

    private void initView(){
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        lnl_register.getLayoutParams().width = (int) Math.round(width * 0.85507246376);
    }

    private void event(){
        img_back.setOnClickListener( v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        btn_register.setOnClickListener( v -> {
            String code = etxt_referral_code.getText().toString();
            String name = etxt_name.getText().toString();
            String phone = etxt_phone.getText().toString();
            String pass = etxt_password.getText().toString();
            String rePass = etxt_re_password.getText().toString();

            Register register = new Register(code, name, phone, pass, rePass);
            presenter.handleRegister(register);
        });
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendVerify(String phoneNumber) {
        VerifyFragment fragment = new VerifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PHONE_NUMBER, phoneNumber);

        fragment.setArguments(bundle);

        Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_login, fragment, null, "register");
    }
}

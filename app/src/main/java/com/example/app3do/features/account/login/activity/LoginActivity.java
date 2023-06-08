package com.example.app3do.features.account.login.activity;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3do.R;
import com.example.app3do.base.BaseActivity;
import com.example.app3do.constans.Constants;
import com.example.app3do.features.account.login.presenter.LoginPresenter;
import com.example.app3do.features.account.login.view.LoginView;
import com.example.app3do.features.account.register.fragment.RegisterFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.models.account.BodyLogin;
import com.example.app3do.models.account.DataLogin;
import com.example.app3do.models.account.Login;
import com.example.app3do.until.direction.Direction;

import java.util.Date;

public class LoginActivity extends BaseActivity implements LoginView {
    LinearLayout lnl_login;
    EditText etxt_phone_number, etxt_password;
    Button btn_login;
    TextView txt_register;
    LoginPresenter presenter;

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        presenter = new LoginPresenter(this);
        init();
        initView();
        event();
    }

    private void init() {
        lnl_login = findViewById(R.id.lnl_login);
        etxt_phone_number = findViewById(R.id.etxt_phone_number);
        etxt_password = findViewById(R.id.etxt_password);
        btn_login = findViewById(R.id.btn_login);
        txt_register = findViewById(R.id.txt_register);
    }

    private void initView(){
        int width = getWindowManager().getDefaultDisplay().getWidth();

        ViewGroup.LayoutParams paramsLnl = lnl_login.getLayoutParams();
        paramsLnl.width = (int) Math.round(width * 0.85507246376);
    }

    private void event(){
        btn_login.setOnClickListener( v -> {
            String username = etxt_phone_number.getText().toString();
            String password = etxt_password.getText().toString();
            Login login = new Login(username, password);

            presenter.handleLogin(login);
        });

        txt_register.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getSupportFragmentManager(), R.id.frame_login, new RegisterFragment(), null, "register");
        });
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LoginSuccess(DataLogin dataLogin) {
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.USER, dataLogin);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
package com.example.app3do.features.account.login.activity;

import androidx.annotation.Nullable;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.app3do.models.account.DataLogin;
import com.example.app3do.models.account.Login;
import com.example.app3do.utils.direction.Direction;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity implements LoginView {
    LinearLayout lnl_login;
    EditText etxt_phone_number, etxt_password;
    Button btn_login;
    TextView txt_register;
    LoginPresenter presenter;
    Dialog mDialog;
    Gson gson;

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

        gson = new Gson();
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.custom_wait);
        mDialog.setCancelable(false);

        Window window = mDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    private void initView(){
        int width = getWindowManager().getDefaultDisplay().getWidth();

        ViewGroup.LayoutParams paramsLnl = lnl_login.getLayoutParams();
        paramsLnl.width = (int) Math.round(width * 0.85507246376);

        if (presenter.getDataLogin(this) != null) {
            mDialog.show();
            Login login = presenter.getDataLogin(this);

            presenter.handleLogin(login);
        }
    }

    private void event(){
        btn_login.setOnClickListener( v -> {
            if (presenter.getDataLogin(this) == null) {
                mDialog.show();
                String username = etxt_phone_number.getText().toString();
                String password = etxt_password.getText().toString();
                Login login = new Login(username, password);

                presenter.handleLogin(login);
            }
        });

        txt_register.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getSupportFragmentManager(), R.id.frame_login, new RegisterFragment(), null, "register");
        });
    }

    @Override
    public void sendMessage(String message) {
        mDialog.cancel();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LoginSuccess(DataLogin dataLogin) {
        String username = etxt_phone_number.getText().toString();
        String password = etxt_password.getText().toString();
        Login login = new Login(username, password);

        presenter.handlePutData(this, login);

        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.USER, dataLogin);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDialog.cancel();
    }
}
package com.example.app3do.features.account.change_password.fragment;

import android.content.res.Resources;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.account.change_password.presenter.ChangePasswordPresenter;
import com.example.app3do.features.account.change_password.view.ChangePasswordView;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.models.account.ChangePassword;

public class ChangePasswordFragment extends BaseFragment implements ChangePasswordView {
    ImageView img_back, img_old_password, img_new_password, img_confirm_new_password;
    Button btn_update_password;
    EditText etxt_old_password, etxt_new_password, etxt_confirm_new_password;
    ChangePasswordPresenter presenter;
    HomeActivity homeActivity;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_change_password;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        presenter = new ChangePasswordPresenter(this);
        homeActivity = (HomeActivity) getActivity();
        img_back = view.findViewById(R.id.img_back);
        img_old_password = view.findViewById(R.id.img_old_password);
        img_new_password = view.findViewById(R.id.img_new_password);
        img_confirm_new_password = view.findViewById(R.id.img_confirm_new_password);
        btn_update_password = view.findViewById(R.id.btn_update_password);
        etxt_old_password = view.findViewById(R.id.etxt_old_password);
        etxt_new_password = view.findViewById(R.id.etxt_new_password);
        etxt_confirm_new_password = view.findViewById(R.id.etxt_confirm_new_password);
    }

    private void initView() {

    }

    private void event() {
        img_back.setOnClickListener( v -> {
            getParentFragmentManager().popBackStack();
        });

        btn_update_password.setOnClickListener( v -> {
            String oldPassword = etxt_old_password.getText().toString();
            String newPassword = etxt_new_password.getText().toString();
            String confirmNewPassword = etxt_confirm_new_password.getText().toString();
            String accessToken = homeActivity.getAccessToken();

            if (newPassword.equals(confirmNewPassword)) {
                ChangePassword password = new ChangePassword(newPassword, oldPassword, accessToken);
                presenter.updatePassword(password);
            } else {
                String error = "Mật khẩu nhập lại không trùng nhau";
                sendMessage(error, false);
            }

        });

        img_old_password.setOnClickListener(v -> {
            if (etxt_old_password.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                etxt_old_password.setTransformationMethod(null);
                etxt_old_password.setSelection(etxt_old_password.length());
                img_old_password.setImageResource(R.drawable.display_eye);
            } else {
                etxt_old_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etxt_old_password.setSelection(etxt_old_password.length());
                img_old_password.setImageResource(R.drawable.hide_eye);
            }
        });

        img_new_password.setOnClickListener(v -> {
            if (etxt_new_password.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                etxt_new_password.setTransformationMethod(null);
                etxt_new_password.setSelection(etxt_new_password.length());
                img_new_password.setImageResource(R.drawable.display_eye);

            } else {
                etxt_new_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etxt_new_password.setSelection(etxt_new_password.length());
                img_new_password.setImageResource(R.drawable.hide_eye);
            }
        });
        img_confirm_new_password.setOnClickListener(v -> {
            if (etxt_confirm_new_password.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                etxt_confirm_new_password.setTransformationMethod(null);
                etxt_confirm_new_password.setSelection(etxt_confirm_new_password.length());
                img_confirm_new_password.setImageResource(R.drawable.display_eye);
            } else {
                etxt_confirm_new_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etxt_confirm_new_password.setSelection(etxt_confirm_new_password.length());
                img_confirm_new_password.setImageResource(R.drawable.hide_eye);
            }
        });
    }

    @Override
    public void sendMessage(String message, boolean isClose) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        if (isClose) {
            getParentFragmentManager().popBackStack();
        }
    }
}

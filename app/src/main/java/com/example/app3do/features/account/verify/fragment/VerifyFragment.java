package com.example.app3do.features.account.verify.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.constans.Constants;
import com.example.app3do.features.account.verify.presenter.VerifyPresenter;
import com.example.app3do.features.account.verify.view.VerifyView;

public class VerifyFragment extends BaseFragment implements VerifyView {
    VerifyPresenter presenter;
    int userID;
    ImageView img_back;
    TextView txt_phone_number, txt_re_send_verify, txt_time_re_send_verify;
    EditText etxt_sms_code_1, etxt_sms_code_2, etxt_sms_code_3, etxt_sms_code_4;
    Button btn_check_confirm_code, btn_register_cussess;
    CountDownTimer countDownTimer;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_vefiry;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view ) {
        presenter = new VerifyPresenter(this);

        img_back = view.findViewById(R.id.img_back);
        txt_phone_number = view.findViewById(R.id.txt_phone_number);
        txt_re_send_verify = view.findViewById(R.id.txt_re_send_verify);
        etxt_sms_code_1 = view.findViewById(R.id.etxt_sms_code_1);
        etxt_sms_code_2 = view.findViewById(R.id.etxt_sms_code_2);
        etxt_sms_code_3 = view.findViewById(R.id.etxt_sms_code_3);
        etxt_sms_code_4 = view.findViewById(R.id.etxt_sms_code_4);
        btn_check_confirm_code = view.findViewById(R.id.btn_check_confirm_code);
        txt_time_re_send_verify = view.findViewById(R.id.txt_time_re_send_verify);

        setUpTimeReSendCode();
    }

    private void initView(){
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        btn_check_confirm_code.getLayoutParams().width = (int) Math.round(width * 0.85507246376);
        txt_phone_number.setText(getArguments().getString(Constants.PHONE_NUMBER));

        setUpLayoutSmsCode();
    }

    private void event(){
        img_back.setOnClickListener( v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        txt_re_send_verify.setOnClickListener( v -> {
            setUpTimeReSendCode();
        });

        btn_check_confirm_code.setOnClickListener( v -> {
            String smsCode1 = etxt_sms_code_1.getText().toString();
            String smsCode2 = etxt_sms_code_2.getText().toString();
            String smsCode3 = etxt_sms_code_3.getText().toString();
            String smsCode4 = etxt_sms_code_4.getText().toString();

            presenter.checkConfirmCode(smsCode1, smsCode2, smsCode3, smsCode4, this.userID);
        });

    }

    private void setUpTimeReSendCode(){
        txt_re_send_verify.setEnabled(false);
        txt_re_send_verify.setTextColor(Color.rgb(129, 136, 143));
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int second = (int) millisUntilFinished / 1000;
                txt_time_re_send_verify.setText(second + "s");
            }

            @Override
            public void onFinish() {
                txt_re_send_verify.setEnabled(true);
                txt_re_send_verify.setTextColor(Color.rgb(21, 135, 248));
                txt_time_re_send_verify.setText("60s");
            }
        };

        countDownTimer.start();

        // Send OTP
        String phoneNumber = getArguments().getString(Constants.PHONE_NUMBER);
        presenter.sendOTP(phoneNumber);
    }

    private void checkWriteOTP(){
        String smsCode_1 = etxt_sms_code_1.getText().toString();
        String smsCode_2 = etxt_sms_code_2.getText().toString();
        String smsCode_3 = etxt_sms_code_3.getText().toString();
        String smsCode_4 = etxt_sms_code_4.getText().toString();

        if(smsCode_1.equals("") || smsCode_2.equals("") || smsCode_3.equals("") || smsCode_4.equals("")) {
            btn_check_confirm_code.setVisibility(View.GONE);
        } else {
            btn_check_confirm_code.setVisibility(View.VISIBLE);
        }
    }

    private void setUpLayoutSmsCode() {
        btn_check_confirm_code.setVisibility(View.GONE);
        etxt_sms_code_1.requestFocus();
        etxt_sms_code_1.setSelection(etxt_sms_code_1.getText().length());

        etxt_sms_code_1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DEL:
                            if(etxt_sms_code_1.isFocusable()) {
                                // 48 ... 57 -> 0 .. 9
                                etxt_sms_code_1.setText("");

                                checkWriteOTP();
                            }
                            break;
                        case KeyEvent.KEYCODE_0:
                        case KeyEvent.KEYCODE_1:
                        case KeyEvent.KEYCODE_2:
                        case KeyEvent.KEYCODE_3:
                        case KeyEvent.KEYCODE_4:
                        case KeyEvent.KEYCODE_5:
                        case KeyEvent.KEYCODE_6:
                        case KeyEvent.KEYCODE_7:
                        case KeyEvent.KEYCODE_8:
                        case KeyEvent.KEYCODE_9:
                            if(etxt_sms_code_1.isFocusable()) {
                                // 48 ... 57 -> 0 .. 9
                                String number = String.valueOf(event.getKeyCode() - 7);
                                etxt_sms_code_1.setText(number);
                                etxt_sms_code_2.requestFocus();
                                etxt_sms_code_2.setSelection(etxt_sms_code_2.getText().length());
                                checkWriteOTP();
                            }
                            break;
                    }
                }
                return false;
            }
        });
        etxt_sms_code_2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DEL:
                            if(etxt_sms_code_2.isFocusable()) {
                                // 48 ... 57 -> 0 .. 9
                                etxt_sms_code_2.setText("");
                                etxt_sms_code_1.requestFocus();
                                etxt_sms_code_1.setSelection(etxt_sms_code_1.getText().length());
                                checkWriteOTP();
                            }
                            break;
                        case KeyEvent.KEYCODE_0:
                        case KeyEvent.KEYCODE_1:
                        case KeyEvent.KEYCODE_2:
                        case KeyEvent.KEYCODE_3:
                        case KeyEvent.KEYCODE_4:
                        case KeyEvent.KEYCODE_5:
                        case KeyEvent.KEYCODE_6:
                        case KeyEvent.KEYCODE_7:
                        case KeyEvent.KEYCODE_8:
                        case KeyEvent.KEYCODE_9:
                            if(etxt_sms_code_2.isFocusable()) {
                                // 48 ... 57 -> 0 .. 9
                                String number = String.valueOf(event.getKeyCode() - 7);
                                etxt_sms_code_2.setText(number);
                                etxt_sms_code_3.requestFocus();
                                etxt_sms_code_3.setSelection(etxt_sms_code_3.getText().length());
                                checkWriteOTP();
                            }
                            break;
                    }
                }
                return false;
            }
        });
        etxt_sms_code_3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DEL:
                            if(etxt_sms_code_3.isFocusable()) {
                                // 48 ... 57 -> 0 .. 9
                                etxt_sms_code_3.setText("");
                                etxt_sms_code_2.requestFocus();
                                etxt_sms_code_2.setSelection(etxt_sms_code_2.getText().length());

                                checkWriteOTP();
                            }
                            break;
                        case KeyEvent.KEYCODE_0:
                        case KeyEvent.KEYCODE_1:
                        case KeyEvent.KEYCODE_2:
                        case KeyEvent.KEYCODE_3:
                        case KeyEvent.KEYCODE_4:
                        case KeyEvent.KEYCODE_5:
                        case KeyEvent.KEYCODE_6:
                        case KeyEvent.KEYCODE_7:
                        case KeyEvent.KEYCODE_8:
                        case KeyEvent.KEYCODE_9:
                            if(etxt_sms_code_3.isFocusable()) {
                                // 48 ... 57 -> 0 .. 9
                                String number = String.valueOf(event.getKeyCode() - 7);
                                etxt_sms_code_3.setText(number);
                                etxt_sms_code_4.requestFocus();
                                etxt_sms_code_4.setSelection(etxt_sms_code_4.getText().length());

                                checkWriteOTP();
                            }
                            break;
                    }
                }
                return false;
            }
        });
        etxt_sms_code_4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DEL:
                            if(etxt_sms_code_4.isFocusable()) {
                                // 48 ... 57 -> 0 .. 9
                                btn_check_confirm_code.setVisibility(View.GONE);
                                etxt_sms_code_4.setText("");
                                etxt_sms_code_3.requestFocus();
                                etxt_sms_code_3.setSelection(etxt_sms_code_3.getText().length());
                                checkWriteOTP();
                            }
                            break;
                        case KeyEvent.KEYCODE_0:
                        case KeyEvent.KEYCODE_1:
                        case KeyEvent.KEYCODE_2:
                        case KeyEvent.KEYCODE_3:
                        case KeyEvent.KEYCODE_4:
                        case KeyEvent.KEYCODE_5:
                        case KeyEvent.KEYCODE_6:
                        case KeyEvent.KEYCODE_7:
                        case KeyEvent.KEYCODE_8:
                        case KeyEvent.KEYCODE_9:
                            if(etxt_sms_code_4.isFocusable()) {
                                // 48 ... 57 -> 0 .. 9
                                String number = String.valueOf(event.getKeyCode() - 7);
                                etxt_sms_code_4.setText(number);
                                etxt_sms_code_4.setSelection(etxt_sms_code_4.getText().length());
                                checkWriteOTP();
                            }
                            break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendUserId(int userId) {
        this.userID = userId;
    }

    @Override
    public void showDialogSuccess() {
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_register_success);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initDialog(dialog);
        evenDialog(dialog);

        dialog.show();
    }
    private void initDialog(Dialog dialog) {
        btn_register_cussess = dialog.findViewById(R.id.btn_register_cussess);
    }
    private void evenDialog(Dialog dialog) {
        btn_register_cussess.setOnClickListener( v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                dialog.cancel();
            }
        });
    }
}

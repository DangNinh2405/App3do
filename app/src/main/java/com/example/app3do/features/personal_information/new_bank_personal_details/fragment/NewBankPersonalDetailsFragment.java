package com.example.app3do.features.personal_information.new_bank_personal_details.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.personal_information.new_bank_personal_details.presenter.NewBankPersonalDetailsPresenter;
import com.example.app3do.features.personal_information.new_bank_personal_details.view.NewBankPersonalDetailsView;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.models.personal.NewbankPersonal;
import com.example.app3do.utils.broadcast.BroadcastUpdatePersonal;

public class NewBankPersonalDetailsFragment extends BaseFragment implements NewBankPersonalDetailsView {
    NewBankPersonalDetailsPresenter presenter;
    HomeActivity homeActivity;
    EditText etxt_account_name, etxt_account_number, etxt_bank_name, etxt_branch;
    LinearLayout lnl_body;
    ImageView img_back;
    Button btn_add_new_bank;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_bank_personal_details;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        homeActivity = (HomeActivity) getActivity();
        presenter = new NewBankPersonalDetailsPresenter(this);
        img_back = view.findViewById(R.id.img_back);
        lnl_body = view.findViewById(R.id.lnl_body);
        btn_add_new_bank = view.findViewById(R.id.btn_add_new_bank);

        etxt_account_name = view.findViewById(R.id.etxt_account_name);
        etxt_account_number = view.findViewById(R.id.etxt_account_number);
        etxt_bank_name = view.findViewById(R.id.etxt_bank_name);
        etxt_branch = view.findViewById(R.id.etxt_branch);
    }

    private void initView() {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        lnl_body.getLayoutParams().width = (int) Math.round(width * 0.85507246376);
    }

    private void event() {
        img_back.setOnClickListener( v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        btn_add_new_bank.setOnClickListener( v -> {
            addNewBank();
        });
    }

    private void addNewBank(){
        String bankName = etxt_bank_name.getText().toString();
        String accountName = etxt_account_name.getText().toString();
        String branch = etxt_branch.getText().toString();
        String accountNumber = etxt_account_number.getText().toString();

        NewbankPersonal newbankPersonal = new NewbankPersonal(bankName, accountName, branch, accountNumber, homeActivity.getAccessToken());
        presenter.addNewBank(newbankPersonal);
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendBroadcast() {
        if (getParentFragmentManager() != null) {
            getParentFragmentManager().popBackStack();
        }

        Intent intent = new Intent(BroadcastUpdatePersonal.ACTION_UPDATE_BANKS);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }
}

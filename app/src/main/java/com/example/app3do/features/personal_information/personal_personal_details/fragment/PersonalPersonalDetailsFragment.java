package com.example.app3do.features.personal_information.personal_personal_details.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.constans.Constants;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.notification.fragment.NotificationFragment;
import com.example.app3do.features.personal_information.change_information.fragment.ChangeInformationFragment;
import com.example.app3do.features.personal_information.personal_personal_details.presenter.PersonalPersonalDetailsPresenter;
import com.example.app3do.features.personal_information.personal_personal_details.view.PersonalPersonalDetailsView;
import com.example.app3do.models.personal.DataPersonal;
import com.example.app3do.until.broadcast.BroadcastUpdateProfile;
import com.example.app3do.until.broadcast.UpdateProfile;
import com.example.app3do.until.direction.Direction;

public class PersonalPersonalDetailsFragment extends BaseFragment implements PersonalPersonalDetailsView, UpdateProfile {
    PersonalPersonalDetailsPresenter presenter;
    BroadcastUpdateProfile receiver = new BroadcastUpdateProfile(this);
    HomeActivity homeActivity;
    TextView txt_name, txt_date_of_birth, txt_gender, txt_cccd_id, txt_email, txt_phone_number, txt_address;
    LinearLayout lnl_information;
    Button btn_delete_account, btn_password_change, btn_update_referral_code, btn_information_change;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_personal_details;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastUpdateProfile.ACTION_PROFILE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }

    private void init(View view) {
        homeActivity = (HomeActivity) getActivity();
        presenter = new PersonalPersonalDetailsPresenter(this);
        txt_name = view.findViewById(R.id.txt_name);
        txt_date_of_birth = view.findViewById(R.id.txt_date_of_birth);
        txt_gender = view.findViewById(R.id.txt_gender);
        txt_cccd_id = view.findViewById(R.id.txt_cccd_id);
        txt_email = view.findViewById(R.id.txt_email);
        txt_phone_number = view.findViewById(R.id.txt_phone_number);
        txt_address = view.findViewById(R.id.txt_address);
        lnl_information = view.findViewById(R.id.lnl_information);
        btn_delete_account = view.findViewById(R.id.btn_delete_account);
        btn_password_change = view.findViewById(R.id.btn_password_change);
        btn_update_referral_code = view.findViewById(R.id.btn_update_referral_code);
        btn_information_change = view.findViewById(R.id.btn_information_change);


    }

    private void initView(){
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        lnl_information.getLayoutParams().width = (int) Math.round(width * 0.89371980676);
        txt_address.getLayoutParams().width = (int) Math.round(width * 0.36473429951);
        presenter.getPersonalInformation(homeActivity.getAccessToken());
    }

    private void event(){
        btn_delete_account.setOnClickListener( v -> {

        });

        btn_password_change.setOnClickListener( v -> {

        });

        btn_update_referral_code.setOnClickListener( v -> {

        });

        btn_information_change.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new ChangeInformationFragment(), null, "change_information");
        });
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(DataPersonal personal) {
        if (personal != null) {
            txt_name.setText(personal.getName());
            txt_date_of_birth.setText(personal.getBirthdays());
            txt_gender.setText(personal.getGender());
            txt_cccd_id.setText(personal.getIdentity().getNumber());
            txt_email.setText(personal.getEmail());
            txt_phone_number.setText(personal.getPhone());
            txt_address.setText(personal.getAddress());
        }
    }

    @Override
    public void updateProfile() {
        presenter.getPersonalInformation(homeActivity.getAccessToken());
    }
}

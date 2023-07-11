package com.example.app3do.features.personal_information.personal_details.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.constans.Constants;
import com.example.app3do.custom.TablayoutPersonalAdapter;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.personal_information.personal_details.presenter.PersonalDetailsPresenter;
import com.example.app3do.features.personal_information.personal_details.view.PersonalDetailsView;
import com.example.app3do.models.personal.DataPersonal;
import com.example.app3do.models.personal.UpdatePersonal;
import com.example.app3do.until.broadcast.BroadcastUpdatePersonal;
import com.example.app3do.until.broadcast.BroadcastUpdateProfile;
import com.example.app3do.until.broadcast.UpdateProfile;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PersonalDetailsFragment extends BaseFragment implements PersonalDetailsView, UpdateProfile {
    PersonalDetailsPresenter presenter;
    BroadcastUpdateProfile receiver = new BroadcastUpdateProfile(this);
    HomeActivity homeActivity;
    ImageView img_back, img_user;
    TabLayout tl_personal_details;
    ViewPager2 vp_personal_details;
    TextView txt_user_name, txt_user_id;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_details;
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
        presenter = new PersonalDetailsPresenter(this);
        img_user = view.findViewById(R.id.img_user);
        img_back = view.findViewById(R.id.img_back);
        tl_personal_details = view.findViewById(R.id.tl_personal_details);
        vp_personal_details = view.findViewById(R.id.vp_personal_details);
        txt_user_name = view.findViewById(R.id.txt_user_name);
        txt_user_id = view.findViewById(R.id.txt_user_id);
    }

    private void initView(){
        presenter.getPersonalInformation(homeActivity.getAccessToken());
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        img_user.getLayoutParams().width = (int) Math.round(width * 0.15700483091) ;
        img_user.getLayoutParams().height = (int) Math.round(width * 0.15700483091) ;

        TablayoutPersonalAdapter adapter = new TablayoutPersonalAdapter(getActivity());
        vp_personal_details.setAdapter(adapter);

        new TabLayoutMediator(tl_personal_details, vp_personal_details, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Cá nhân");
                    break;
                case 1:
                    tab.setText("Sổ địa chỉ");
                    break;
                case 2:
                    tab.setText("Thông tin ngân hàng");
                    break;
                default:
                    tab.setText("Cá nhân");
                    break;
            }
        }).attach();
    }

    private void event(){
        img_back.setOnClickListener( v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(DataPersonal personal) {
        if (personal != null) {
            Glide.with(getContext()).load(personal.getAvatar()).into(img_user);
            txt_user_name.setText(personal.getName());
            txt_user_id.setText("MGT" + personal.getId());
        }
    }

    @Override
    public void updateProfile() {
        presenter.getPersonalInformation(homeActivity.getAccessToken());
    }
}
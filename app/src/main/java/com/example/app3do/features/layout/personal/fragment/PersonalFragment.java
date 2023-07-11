package com.example.app3do.features.layout.personal.fragment;

import android.content.IntentFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.personal_information.personal_details.fragment.PersonalDetailsFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.layout.personal.presenter.PersonalPresenter;
import com.example.app3do.features.layout.personal.view.PersonalView;
import com.example.app3do.models.personal.DataPersonal;
import com.example.app3do.until.broadcast.BroadcastUpdateProfile;
import com.example.app3do.until.broadcast.BroadcastUpdatePersonal;
import com.example.app3do.until.broadcast.UpdatePersonal;
import com.example.app3do.until.broadcast.UpdateProfile;
import com.example.app3do.until.direction.Direction;

import java.text.NumberFormat;
import java.util.Locale;

public class PersonalFragment extends BaseFragment implements PersonalView, UpdatePersonal, UpdateProfile {
    Locale locale;
    BroadcastUpdatePersonal receiver = new BroadcastUpdatePersonal(this);
    BroadcastUpdateProfile updateProfile = new BroadcastUpdateProfile(this);
    NumberFormat format;
    PersonalPresenter presenter;
    HomeActivity homeActivity;
    ImageView img_user, img_rating, img_material;
    RelativeLayout rltl_heaeder;
    ProgressBar pg_level;
    DataPersonal personal;
    LinearLayout lnl_wallet_point;
    TextView txt_user_name, txt_user_id, txt_current_level_commission, txt_next_level_commission, txt_owner_commission, txt_total_ord_amount, txt_personal_details, txt_material;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    @Override
    public void onStart() {
        IntentFilter filter = new IntentFilter();
        IntentFilter filter2 = new IntentFilter();

        filter.addAction(BroadcastUpdatePersonal.ACTION_UPDATE_BANKS);
        filter.addAction(BroadcastUpdatePersonal.ACTION_UPDATE_ADDRESSES);
        filter2.addAction(BroadcastUpdateProfile.ACTION_PROFILE);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filter);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(updateProfile, filter2);
        super.onStart();
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(updateProfile);

        super.onStop();
    }

    private void init(View view) {
        homeActivity = (HomeActivity) getActivity();
        presenter = new PersonalPresenter(this);
        locale = new Locale("vi", "VN");
        format = NumberFormat.getCurrencyInstance(locale);

        rltl_heaeder = view.findViewById(R.id.rltl_heaeder);
        img_user = view.findViewById(R.id.img_user);
        pg_level = view.findViewById(R.id.pg_level);
        lnl_wallet_point = view.findViewById(R.id.lnl_wallet_point);
        txt_user_name = view.findViewById(R.id.txt_user_name);
        txt_user_id = view.findViewById(R.id.txt_user_id);
        txt_current_level_commission = view.findViewById(R.id.txt_current_level_commission);
        txt_next_level_commission = view.findViewById(R.id.txt_next_level_commission);
        txt_personal_details = view.findViewById(R.id.txt_personal_details);
        txt_total_ord_amount = view.findViewById(R.id.txt_total_ord_amount);
        txt_owner_commission = view.findViewById(R.id.txt_owner_commission);
        txt_material = view.findViewById(R.id.txt_material);
        img_rating = view.findViewById(R.id.img_rating);
        img_material = view.findViewById(R.id.img_material);
    }

    private void initView() {
        presenter.getPersonalInformation(homeActivity.getAccessToken());

        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        img_user.getLayoutParams().width = (int) Math.round(width * 0.15700483091);
        img_user.getLayoutParams().height = (int) Math.round(width * 0.15700483091);

        rltl_heaeder.getLayoutParams().width = (int) Math.round(width * 0.88405797101);
        pg_level.getLayoutParams().width = (int) Math.round(width * 0.92270531401);
        lnl_wallet_point.getLayoutParams().width = (int) Math.round(width * 0.92270531401);
    }

    private void event() {
        txt_personal_details.setOnClickListener(v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new PersonalDetailsFragment(), null, "personal_details");

        });
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void renderPersonalInformation(DataPersonal personal) {
        if (personal != null) {
            this.personal = personal;

            Glide.with(getContext()).load(personal.getAvatar()).into(img_user);
            txt_user_name.setText(personal.getName());
            txt_user_id.setText("MGT " + personal.getId());

            txt_next_level_commission.setText(String.valueOf(personal.getLevelProgress().getNextLevelCommission()));
            txt_current_level_commission.setText(String.valueOf(personal.getLevelProgress().getCurrentLevelCommission()));
            pg_level.setMax(personal.getLevelProgress().getNextLevelCommission());
            pg_level.setProgress(personal.getLevelProgress().getCurrentLevelCommission());
            txt_total_ord_amount.setText(format.format(personal.getTotalOrdAmount()));
            txt_owner_commission.setText(String.valueOf(personal.getOwnerCommission()));

            String material = "";
            String rating = "";

            switch (personal.getLevel()) {
                case 1:
                    img_material.setImageResource(R.drawable.bronze_2x);
                    material = "Đồng";
                    break;
                case 2:
                    img_material.setImageResource(R.drawable.silver_2x);
                    material = "Bạc";
                    break;
                case 3:
                    img_material.setImageResource(R.drawable.gold_2x);
                    material = "Vàng";
                    break;
                case 4:
                    img_material.setImageResource(R.drawable.platinum_2x);
                    material = "Bạch kim";
                    break;
                case 5:
                    img_material.setImageResource(R.drawable.diamond_2x);
                    material = "Kim cương";
                    break;
            }

            switch (personal.getLevelProgress().getLevel()) {
                case 1:
                    img_rating.setImageResource(R.drawable.rating_1_2x);
                    rating = " I";
                    break;
                case 2:
                    img_rating.setImageResource(R.drawable.rating_2_2x);
                    rating = " II";
                    break;
                case 3:
                    img_rating.setImageResource(R.drawable.rating_3_2x);
                    rating = " III";
                    break;
                case 4:
                    img_rating.setImageResource(R.drawable.rating_4_2x);
                    rating = " IV";
                    break;
                case 5:
                    img_rating.setImageResource(R.drawable.rating_5_2x);
                    rating = " V";
                    break;
                case 6:
                    img_rating.setImageResource(R.drawable.rating_6_2x);
                    rating = " VI";
                    break;
                case 7:
                    img_rating.setImageResource(R.drawable.rating_7_2x);
                    rating = " VII";
                    break;
                case 8:
                    img_rating.setImageResource(R.drawable.rating_8_2x);
                    rating = " VIII";
                    break;
                case 9:
                    img_rating.setImageResource(R.drawable.rating_9_2x);
                    rating = " IX";
                    break;
                case 10:
                    img_rating.setImageResource(R.drawable.rating_10_2x);
                    rating = " X";
                    break;
            }

            txt_material.setText(material + rating);
        }
    }

    @Override
    public void updateAddresses() {
        presenter.getPersonalInformation(homeActivity.getAccessToken());
    }

    @Override
    public void updateBanks() {
        presenter.getPersonalInformation(homeActivity.getAccessToken());
    }

    @Override
    public void updateProfile() {
        presenter.getPersonalInformation(homeActivity.getAccessToken());
    }
}

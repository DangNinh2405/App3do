package com.example.app3do.features.layout.personal.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.personal_information.app_information.fragment.AppInformationFragment;
import com.example.app3do.features.personal_information.banks_account.fragment.BanksAccountFragment;
import com.example.app3do.features.personal_information.contact_help.fragment.ContactHelpFragment;
import com.example.app3do.features.personal_information.exchange_points.fragment.ExchangePointsFragment;
import com.example.app3do.features.personal_information.points_history.fragment.PointsHistoryFragment;
import com.example.app3do.features.post.post_category.fragment.PostCategoryFragment;
import com.example.app3do.features.personal_information.personal_details.fragment.PersonalDetailsFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.layout.personal.presenter.PersonalPresenter;
import com.example.app3do.features.personal_information.wallet_history.fragment.WalletHistoryFragment;
import com.example.app3do.features.layout.personal.view.PersonalView;
import com.example.app3do.models.personal.DataPersonal;
import com.example.app3do.utils.broadcast.BroadcastUpdateProfile;
import com.example.app3do.utils.broadcast.BroadcastUpdatePersonal;
import com.example.app3do.utils.broadcast.UpdatePersonal;
import com.example.app3do.utils.broadcast.UpdateProfile;
import com.example.app3do.utils.direction.Direction;

import java.text.NumberFormat;
import java.util.Locale;

public class PersonalFragment extends BaseFragment implements PersonalView, UpdatePersonal, UpdateProfile {
    public final static String TYPE_NEWS = "NEWS";
    public final static String TYPE_POLICY = "POLICY";
    public final static String TYPE_VIDEO = "VIDEO";
    public final static String TYPE_ORIENTATION = "ORIENTATION";
    public final static String KEY = "KEY";
    public final static String DATA_PERSONAL = "DATA_PERSONAL";
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
    TextView txt_user_name, txt_log_out, txt_orientation, txt_video, txt_policy, txt_event_news, txt_app_information, txt_user_id, txt_current_level_commission, txt_next_level_commission, txt_owner_commission, txt_total_ord_amount, txt_personal_details, txt_material, txt_contact_help;
    Button btn_bank_accounts, btn_history_wallet, btn_exchange_points, btn_points_history, btn_cancel, btn_log_out;
    Dialog mDialog;

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
        txt_contact_help = view.findViewById(R.id.txt_contact_help);
        txt_app_information = view.findViewById(R.id.txt_app_information);
        txt_event_news = view.findViewById(R.id.txt_event_news);
        txt_policy = view.findViewById(R.id.txt_policy);
        txt_orientation = view.findViewById(R.id.txt_orientation);
        txt_video = view.findViewById(R.id.txt_video);
        btn_bank_accounts = view.findViewById(R.id.btn_bank_accounts);
        btn_history_wallet = view.findViewById(R.id.btn_history_wallet);
        btn_exchange_points = view.findViewById(R.id.btn_exchange_points);
        btn_points_history = view.findViewById(R.id.btn_points_history);
        txt_log_out = view.findViewById(R.id.txt_log_out);
    }

    private void initView() {
        presenter.getPersonalInformation(homeActivity.getAccessToken());

        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        img_user.getLayoutParams().width = (int) Math.round(width * 0.15700483091);
        img_user.getLayoutParams().height = (int) Math.round(width * 0.15700483091);

        rltl_heaeder.getLayoutParams().width = (int) Math.round(width * 0.88405797101);
        pg_level.getLayoutParams().width = (int) Math.round(width * 0.92270531401);
        lnl_wallet_point.getLayoutParams().width = (int) Math.round(width * 0.92270531401);

        mDialog = new Dialog(getContext());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.custom_log_out);
        mDialog.setCancelable(false);

        Window window = mDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initDialog(mDialog);
        eventDialog(mDialog);
    }

    private void event() {
        txt_personal_details.setOnClickListener(v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new PersonalDetailsFragment(), null, "personal_details");
        });

        txt_contact_help.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new ContactHelpFragment(), null, "contact_help");
        });

        txt_app_information.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new AppInformationFragment(), null, "app_information");
        });

        txt_event_news.setOnClickListener( v -> {
            directionToFragment(TYPE_NEWS, TYPE_NEWS.toLowerCase());
        });

        txt_policy.setOnClickListener( v -> {
            directionToFragment(TYPE_POLICY, TYPE_POLICY.toLowerCase());
        });

        txt_orientation.setOnClickListener( v -> {
            directionToFragment(TYPE_ORIENTATION, TYPE_ORIENTATION.toLowerCase());
        });

        txt_video.setOnClickListener( v -> {
            directionToFragment(TYPE_VIDEO, TYPE_VIDEO.toLowerCase());
        });

        btn_bank_accounts.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new BanksAccountFragment(), null, "bank_accounts");
        });

        btn_history_wallet.setOnClickListener( v -> {
            WalletHistoryFragment frament = new WalletHistoryFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(DATA_PERSONAL, personal);
            frament.setArguments(bundle);

            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, frament, null, "wallet_history");
        });

        btn_exchange_points.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new ExchangePointsFragment(), null, "exchange_points");
        });

        btn_points_history.setOnClickListener( v -> {
            PointsHistoryFragment frament = new PointsHistoryFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(DATA_PERSONAL, personal);
            frament.setArguments(bundle);

            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, frament, null, "points_history");
        });

        txt_log_out.setOnClickListener( v -> {
            mDialog.show();

        });
    }

    private void initDialog(Dialog dialog){
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_log_out = dialog.findViewById(R.id.btn_log_out);
    }

    private void eventDialog(Dialog dialog) {
        btn_cancel.setOnClickListener( v -> {
            dialog.cancel();
        });

        btn_log_out.setOnClickListener( v -> {
            presenter.clearDataShareReference(homeActivity);
            homeActivity.finish();
        });
    }

    @Override
    public void onDestroy() {
        mDialog.cancel();
        super.onDestroy();
    }

    private void directionToFragment(String key, String backStack) {
        PostCategoryFragment fragment = new PostCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, key);
        fragment.setArguments(bundle);

        Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, fragment, null, backStack);
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

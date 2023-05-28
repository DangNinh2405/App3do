package com.example.app3do.features.layout.personal.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.information.personal_details.fragment.PersonalDetailsFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.layout.personal.presenter.PersonalPresenter;
import com.example.app3do.features.layout.personal.view.PersonalView;
import com.example.app3do.models.personal_information.DataPersonal;
import com.example.app3do.until.direction.Direction;

import java.text.NumberFormat;
import java.util.Locale;

public class PersonalFragment extends BaseFragment implements PersonalView {
    Locale locale;
    NumberFormat format;
    PersonalPresenter presenter;
    HomeActivity homeActivity;
    ImageView img_user;
    RelativeLayout rltl_heaeder;
    ProgressBar pg_level;
    LinearLayout lnl_wallet_point;
    TextView txt_user_name, txt_user_id, txt_current_level_commission, txt_next_level_commission, txt_owner_commission, txt_total_order_success, txt_personal_details;

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

    private void init(View view ) {
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
        txt_total_order_success = view.findViewById(R.id.txt_total_order_success);
        txt_owner_commission = view.findViewById(R.id.txt_owner_commission);
    }

    private void initView() {
        presenter.handlePersonalInformation(homeActivity.getAccessToken());

        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        img_user.getLayoutParams().width = (int) Math.round(width * 0.15700483091);
        img_user.getLayoutParams().height = (int) Math.round(width * 0.15700483091);

        rltl_heaeder.getLayoutParams().width = (int) Math.round(width * 0.88405797101);
        pg_level.getLayoutParams().width = (int) Math.round(width * 0.92270531401);
        lnl_wallet_point.getLayoutParams().width = (int) Math.round(width * 0.92270531401);
    }

    private void event() {
        txt_personal_details.setOnClickListener( v -> {
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
            Glide.with(getContext()).load(personal.getAvatar()).into(img_user);
            txt_user_name.setText(personal.getName());
            txt_user_id.setText("MGT " + personal.getId());

            txt_next_level_commission.setText(String.valueOf(personal.getLevelProgress().getNextLevelCommission()));
            txt_current_level_commission.setText(String.valueOf(personal.getLevelProgress().getCurrentLevelCommission()));
            pg_level.setMax(personal.getLevelProgress().getNextLevelCommission());
            pg_level.setProgress(personal.getLevelProgress().getCurrentLevelCommission());
            txt_total_order_success.setText(format.format(personal.getTotalOrderSuccess()));
            txt_owner_commission.setText(String.valueOf(personal.getOwnerCommission()));
        }
    }
}

package com.example.app3do.features.personal_information.points_history.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.layout.personal.fragment.PersonalFragment;
import com.example.app3do.features.personal_information.points_history.view.PointsHistoryView;
import com.example.app3do.models.personal.DataPersonal;


public class PointsHistoryFragment extends BaseFragment implements PointsHistoryView {
    ImageView img_back;
    DataPersonal personal;
    ImageView img_user;
    TextView txt_user_name, txt_user_id, txt_owner_commission;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_points_history;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        personal = (DataPersonal) getArguments().getSerializable(PersonalFragment.DATA_PERSONAL);
        img_back = view.findViewById(R.id.img_back);
        img_user = view.findViewById(R.id.img_user);
        txt_user_name = view.findViewById(R.id.txt_user_name);
        txt_user_id = view.findViewById(R.id.txt_user_id);
        txt_owner_commission = view.findViewById(R.id.txt_owner_commission);
    }

    private void initView() {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        img_user.getLayoutParams().width = (int) Math.round(width * 0.15700483091) ;
        img_user.getLayoutParams().height = (int) Math.round(width * 0.15700483091) ;

        if (personal != null) {
            Glide.with(getContext()).load(personal.getAvatar()).into(img_user);
            txt_user_name.setText(personal.getName());
            txt_user_id.setText("MGT" + personal.getId());
            txt_owner_commission.setText(personal.getOwnerCommission() + "");
        }
    }

    private void event() {
        img_back.setOnClickListener( v -> {
            getParentFragmentManager().popBackStack();
        });
    }
}

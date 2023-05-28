package com.example.app3do.features.information.personal_details.fragment;

import android.view.View;
import android.widget.ImageView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.custom.TablayoutPersonalAdapter;
import com.example.app3do.features.information.personal_details.view.PersonalDetailsView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PersonalDetailsFragment extends BaseFragment implements PersonalDetailsView {
    ImageView img_back, img_user;
    TabLayout tl_personal_details;
    ViewPager2 vp_personal_details;

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

    private void init(View view) {
        img_user = view.findViewById(R.id.img_user);
        img_back = view.findViewById(R.id.img_back);
        tl_personal_details = view.findViewById(R.id.tl_personal_details);
        vp_personal_details = view.findViewById(R.id.vp_personal_details);
    }

    private void initView(){
        TablayoutPersonalAdapter adapter = new TablayoutPersonalAdapter(getActivity());
        vp_personal_details.setAdapter(adapter);

        new TabLayoutMediator(tl_personal_details, vp_personal_details, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Cá nhân");
                    break;
                case 1:
                    tab.setText("sổ địa chỉ");
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
}
package com.example.app3do.features.personal_information.app_information.fragment;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.personal_information.app_information.view.AppInformationView;

public class AppInformationFragment extends BaseFragment implements AppInformationView {
    TextView txt_content_1, txt_content_2, txt_content_3, txt_content_4,txt_content_5, txt_content_6, txt_content_7, txt_content_8;
    ImageView img_back;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_app_information;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        img_back = view.findViewById(R.id.img_back);
        txt_content_1 = view.findViewById(R.id.txt_content_1);
        txt_content_2 = view.findViewById(R.id.txt_content_2);
        txt_content_3 = view.findViewById(R.id.txt_content_3);
        txt_content_4 = view.findViewById(R.id.txt_content_4);
        txt_content_5 = view.findViewById(R.id.txt_content_5);
        txt_content_6 = view.findViewById(R.id.txt_content_6);
        txt_content_7 = view.findViewById(R.id.txt_content_7);
        txt_content_8 = view.findViewById(R.id.txt_content_8);
    }

    private void initView() {
        txt_content_1.setText(R.string.content_1);
        txt_content_2.setText(R.string.content_2);
        txt_content_3.setText(R.string.content_3);
        txt_content_4.setText(R.string.content_4);
        txt_content_5.setText(R.string.content_5);
        txt_content_6.setText(R.string.content_6);
        txt_content_7.setText(R.string.content_7);
        txt_content_8.setText(R.string.content_8);
    }

    private void event() {
        img_back.setOnClickListener( v -> {
            getParentFragmentManager().popBackStack();
        });
    }
}

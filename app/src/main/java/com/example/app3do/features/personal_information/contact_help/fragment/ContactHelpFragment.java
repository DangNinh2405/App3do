package com.example.app3do.features.personal_information.contact_help.fragment;

import android.view.View;
import android.widget.ImageView;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.personal_information.contact_help.view.ContactHelpView;

public class ContactHelpFragment extends BaseFragment implements ContactHelpView {
    private ImageView img_back;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_contact_help;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        event();
    }

    private void event() {
        img_back.setOnClickListener( v -> {
            getParentFragmentManager().popBackStack();
        });
    }

    private void init(View view) {
        img_back = view.findViewById(R.id.img_back);
    }
}

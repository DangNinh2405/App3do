package com.example.app3do.custom;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app3do.features.information.personal_personal_details.fragment.PersonalPersonalDetailsFragment;

public class TablayoutPersonalAdapter extends FragmentStateAdapter {
    public TablayoutPersonalAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PersonalPersonalDetailsFragment();
            case 1:
                return new PersonalPersonalDetailsFragment();
            case 2:
                return new PersonalPersonalDetailsFragment();
            default:
                return new PersonalPersonalDetailsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

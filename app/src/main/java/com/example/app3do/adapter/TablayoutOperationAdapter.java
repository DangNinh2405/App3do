package com.example.app3do.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app3do.features.order.personal.fragment.PersonalOrdersFragment;
import com.example.app3do.features.order.system.fragment.SystemOrdersFragment;

public class TablayoutOperationAdapter extends FragmentStateAdapter {
    public TablayoutOperationAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PersonalOrdersFragment();
            case 1:
                return new SystemOrdersFragment();
            default:
                return new PersonalOrdersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

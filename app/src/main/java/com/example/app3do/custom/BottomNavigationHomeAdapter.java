package com.example.app3do.custom;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app3do.features.layout.home.fragment.HomeFragment;
import com.example.app3do.features.layout.operation.fragment.OperationFragment;
import com.example.app3do.features.layout.personal.fragment.PersonalFragment;
import com.example.app3do.features.layout.report.fragment.ReportFragment;
import com.example.app3do.features.layout.system.fragment.SystemFragment;

public class BottomNavigationHomeAdapter extends FragmentStateAdapter {
    public BottomNavigationHomeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SystemFragment();
            case 2:
                return new OperationFragment();
            case 3:
                return new ReportFragment();
            case 4:
                return new PersonalFragment();
            default:
                return new HomeFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}

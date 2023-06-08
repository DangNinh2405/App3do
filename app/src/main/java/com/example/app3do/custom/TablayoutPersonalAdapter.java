package com.example.app3do.custom;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app3do.features.personal_information.addresses_personal_details.fragment.AddressesPersonalDetailsFragment;
import com.example.app3do.features.personal_information.banks_personal_details.fragment.BanksPersonalDetailsFragment;
import com.example.app3do.features.personal_information.personal_personal_details.fragment.PersonalPersonalDetailsFragment;
import com.example.app3do.models.personal.DataPersonal;

public class TablayoutPersonalAdapter extends FragmentStateAdapter {
    private DataPersonal personal;

    public TablayoutPersonalAdapter(@NonNull FragmentActivity fragmentActivity, DataPersonal personal) {
        super(fragmentActivity);
        this.personal = personal;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PersonalPersonalDetailsFragment(this.personal);
            case 1:
                return new AddressesPersonalDetailsFragment(this.personal);
            case 2:
                return new BanksPersonalDetailsFragment(this.personal);
            default:
                return new PersonalPersonalDetailsFragment(this.personal);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

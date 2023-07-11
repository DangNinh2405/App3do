package com.example.app3do.features.personal_information.addresses_personal_details.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.custom.AddressesPersonalAdapter;
import com.example.app3do.features.personal_information.addresses_personal_details.presenter.AddressesPersonalDetailsPresenter;
import com.example.app3do.features.personal_information.addresses_personal_details.view.AddressesPersonalDetailsView;
import com.example.app3do.features.personal_information.new_address_personal_details.fragment.NewAddressPersonalDetailsFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.models.personal.AddressesPersonal;
import com.example.app3do.models.personal.DataPersonal;
import com.example.app3do.until.broadcast.BroadcastUpdatePersonal;
import com.example.app3do.until.broadcast.UpdatePersonal;
import com.example.app3do.until.direction.Direction;

public class AddressesPersonalDetailsFragment extends BaseFragment implements AddressesPersonalDetailsView, UpdatePersonal {
    HomeActivity homeActivity;
    BroadcastUpdatePersonal receiver = new BroadcastUpdatePersonal(this);
    AddressesPersonalDetailsPresenter presenter;

    Button btn_add_address;
    RecyclerView rcv_addresses;
    AddressesPersonalAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_addresses_personal_details;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public void onStart() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastUpdatePersonal.ACTION_UPDATE_ADDRESSES);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filter);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        rcv_addresses.setAdapter(null);
        rcv_addresses.setLayoutManager(null);
        rcv_addresses.removeAllViews();
        rcv_addresses = null;

        super.onDestroy();
    }

    private void init(View view) {
        homeActivity = (HomeActivity) getActivity();
        presenter = new AddressesPersonalDetailsPresenter(this);
        btn_add_address = view.findViewById(R.id.btn_add_address);
        rcv_addresses = view.findViewById(R.id.rcv_addresses);
    }

    private void initView(){
        presenter.getPersonalInformation(homeActivity.getAccessToken());
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        rcv_addresses.getLayoutParams().width = (int) Math.round(width * 0.92270531401);
        btn_add_address.getLayoutParams().width = (int) Math.round(width * 0.85507246376);
    }

    private void eventAdapter(AddressesPersonalAdapter addressAdapter) {
        addressAdapter.setItemOnClickListener(new AddressesPersonalAdapter.ItemOnClickListener() {
            @Override
            public void onEdit(AddressesPersonal address) {
                Toast.makeText(getContext(), address.getId() + ": Edit", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClear(AddressesPersonal address) {
                presenter.deleteAddressPersonal(homeActivity.getAccessToken(), address.getId());
            }
        });
    }

    private void event() {
        btn_add_address.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new NewAddressPersonalDetailsFragment(), null, "new_address");
        });
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(BroadcastUpdatePersonal.ACTION_UPDATE_ADDRESSES);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    @Override
    public void renderView(DataPersonal dataPersonal) {
        if (dataPersonal != null) {
            if (adapter == null) {
                adapter = new AddressesPersonalAdapter(dataPersonal.getAddresses());
                rcv_addresses.setAdapter(adapter);
                rcv_addresses.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                adapter.updateData(dataPersonal.getAddresses());
                adapter.notifyDataSetChanged();
            }

            eventAdapter(adapter);
        }
    }

    @Override
    public void updateAddresses() {
        presenter.getPersonalInformation(homeActivity.getAccessToken());
    }

    @Override
    public void updateBanks() {

    }
}
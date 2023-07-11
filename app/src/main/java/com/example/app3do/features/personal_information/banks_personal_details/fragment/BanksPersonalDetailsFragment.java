package com.example.app3do.features.personal_information.banks_personal_details.fragment;

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
import com.example.app3do.custom.BanksPersonalAdapter;
import com.example.app3do.features.personal_information.banks_personal_details.presenter.BanksPersonalDetailsPresenter;
import com.example.app3do.features.personal_information.banks_personal_details.view.BanksPersonalDetailsView;
import com.example.app3do.features.personal_information.new_bank_personal_details.fragment.NewBankPersonalDetailsFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.models.personal.DataPersonal;
import com.example.app3do.until.broadcast.BroadcastUpdatePersonal;
import com.example.app3do.until.broadcast.UpdatePersonal;
import com.example.app3do.until.direction.Direction;

public class BanksPersonalDetailsFragment extends BaseFragment implements BanksPersonalDetailsView, UpdatePersonal {
    BanksPersonalAdapter adapter;
    BroadcastUpdatePersonal receiver = new BroadcastUpdatePersonal(this);
    BanksPersonalDetailsPresenter presenter;
    HomeActivity homeActivity;


    RecyclerView rcv_banks;
    Button btn_add_banks;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_banks_personal_details;
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
        filter.addAction(BroadcastUpdatePersonal.ACTION_UPDATE_BANKS);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filter);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        rcv_banks.removeAllViews();
        rcv_banks.setAdapter(null);
        rcv_banks.setLayoutManager(null);
        rcv_banks = null;
        super.onDestroy();
    }

    private void init(View view) {
        homeActivity = (HomeActivity) getActivity();
        presenter = new BanksPersonalDetailsPresenter(this);
        btn_add_banks = view.findViewById(R.id.btn_add_banks);
        rcv_banks = view.findViewById(R.id.rcv_banks);
    }

    private void initView() {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        rcv_banks.getLayoutParams().width = (int) Math.round(width * 0.92270531401);
        btn_add_banks.getLayoutParams().width = (int) Math.round(width * 0.85507246376);

        presenter.getPersonalInformation(homeActivity.getAccessToken());
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(BroadcastUpdatePersonal.ACTION_UPDATE_BANKS);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    @Override
    public void renderView(DataPersonal dataPersonal) {
        if (dataPersonal != null) {
            if (adapter == null) {
                adapter = new BanksPersonalAdapter(dataPersonal.getBanks());
                rcv_banks.setAdapter(adapter);
                rcv_banks.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                adapter.updateData(dataPersonal.getBanks());
                adapter.notifyDataSetChanged();
            }

            eventAdapter(adapter);
        }
    }

    private void eventAdapter(BanksPersonalAdapter banksPersonalAdapter) {
        banksPersonalAdapter.setItemOnClickListener(bank -> {
            presenter.deleteBankPersonal(homeActivity.getAccessToken(), bank.getId());
        });
    }

    private void event(){
        btn_add_banks.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new NewBankPersonalDetailsFragment(), null, "new_bank");
        });
    }

    @Override
    public void updateAddresses() {

    }

    @Override
    public void updateBanks() {
        presenter.getPersonalInformation(homeActivity.getAccessToken());
    }
}

package com.example.app3do.features.personal_information.wallet_history.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.layout.personal.fragment.PersonalFragment;
import com.example.app3do.features.personal_information.banks_account.fragment.BanksAccountFragment;
import com.example.app3do.features.personal_information.wallet_history.prensenter.WalletHistoryPresenter;
import com.example.app3do.features.personal_information.wallet_history.view.WalletHistoryView;
import com.example.app3do.models.personal.DataPersonal;
import com.example.app3do.utils.direction.Direction;

import java.text.NumberFormat;
import java.util.Locale;

public class WalletHistoryFragment extends BaseFragment implements WalletHistoryView {
    ImageView img_back, img_user;
    WalletHistoryPresenter presenter;
    HomeActivity homeActivity;
    TextView txt_user_name, txt_user_id, txt_total_ord_amount;
    DataPersonal personal;
    ProgressBar pg_loading;

    Button btn_payment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wallet_history;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        presenter = new WalletHistoryPresenter(this);
        homeActivity = (HomeActivity) getActivity();
        personal = (DataPersonal) getArguments().getSerializable(PersonalFragment.DATA_PERSONAL);
        img_back = view.findViewById(R.id.img_back);
        img_user = view.findViewById(R.id.img_user);
        txt_user_name = view.findViewById(R.id.txt_user_name);
        txt_user_id = view.findViewById(R.id.txt_user_id);
        txt_total_ord_amount = view.findViewById(R.id.txt_total_ord_amount);
        pg_loading = view.findViewById(R.id.pg_loading);
        btn_payment = view.findViewById(R.id.btn_payment);
    }

    private void initView() {
        presenter.getMoneyLogs(homeActivity.getAccessToken());

        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        img_user.getLayoutParams().width = (int) Math.round(width * 0.15700483091) ;
        img_user.getLayoutParams().height = (int) Math.round(width * 0.15700483091) ;

        if (personal != null) {
            Glide.with(getContext()).load(personal.getAvatar()).into(img_user);
            txt_user_name.setText(personal.getName());
            txt_user_id.setText("MGT" + personal.getId());
            txt_total_ord_amount.setText(format.format(personal.getTotalOrdAmount()));
        }
    }

    private void event(){
        img_back.setOnClickListener( v -> {
            getParentFragmentManager().popBackStack();
        });

        btn_payment.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new BanksAccountFragment(), null, "bank_accounts");
        });
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createWalletHistory() {

    }

    @Override
    public void loading(boolean isLoading) {
        if (isLoading){
            pg_loading.setVisibility(View.VISIBLE);
        } else {
            pg_loading.setVisibility(View.GONE);
        }
    }
}

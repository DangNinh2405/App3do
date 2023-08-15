package com.example.app3do.features.personal_information.new_address_personal_details.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.adapter.AddressDialogAdapter;
import com.example.app3do.features.personal_information.new_address_personal_details.presenter.NewAddressPersonalDetailsPresenter;
import com.example.app3do.features.personal_information.new_address_personal_details.view.NewAddressPersonalDetailsView;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.models.personal.AddAddress;
import com.example.app3do.models.personal.WardDistrictProvincePersonal;
import com.example.app3do.utils.broadcast.BroadcastUpdatePersonal;

import java.util.List;

public class NewAddressPersonalDetailsFragment extends BaseFragment implements NewAddressPersonalDetailsView {
    private final String WARD = "WARD";
    private final String DISTRICT = "DISTRICT";
    private final String PROVINCE = "PROVINCE";
    private final String TITLE = "Địa chỉ";
    NewAddressPersonalDetailsPresenter presenter;
    HomeActivity homeActivity;
    ImageView img_back;
    RadioGroup rg_address;
    TextView txt_ward, txt_district, txt_province;
    Button btn_add_address;
    LinearLayout lnl_body;
    WardDistrictProvincePersonal Ward;
    WardDistrictProvincePersonal District;
    WardDistrictProvincePersonal Province;
    EditText etxt_address_name, etxt_address_phone, etxt_address;

    // Dialog
    TextView txt_title;
    RecyclerView rcv_select_address;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_address_personal_details;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        presenter = new NewAddressPersonalDetailsPresenter(this);
        homeActivity = (HomeActivity) getActivity();

        img_back = view.findViewById(R.id.img_back);
        lnl_body = view.findViewById(R.id.lnl_body);
        txt_ward = view.findViewById(R.id.txt_ward);
        txt_district = view.findViewById(R.id.txt_district);
        txt_province = view.findViewById(R.id.txt_province);
        rg_address = view.findViewById(R.id.rg_address);
        btn_add_address = view.findViewById(R.id.btn_add_address);
        etxt_address_name = view.findViewById(R.id.etxt_address_name);
        etxt_address_phone = view.findViewById(R.id.etxt_address_phone);
        etxt_address = view.findViewById(R.id.etxt_address);
    }

    private void initView() {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        lnl_body.getLayoutParams().width = (int) Math.round(width * 0.85507246376);
    }

    private void event() {
        img_back.setOnClickListener( v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        txt_ward.setOnClickListener( v -> {
            presenter.createSelectWard(homeActivity.getAccessToken(), txt_ward, District);
        });

        txt_district.setOnClickListener( v -> {
            presenter.createSelectDistrict(homeActivity.getAccessToken(), txt_district, Province);
        });

        txt_province.setOnClickListener( v -> {
            presenter.createSelectProvince(homeActivity.getAccessToken(), txt_province);
        });

        btn_add_address.setOnClickListener( v -> {
            addAddress();
        });
    }

    private void addAddress() {
        if (Ward == null) {
            sendMessage("Quý khách chưa hoàn thiện thông tin phường/xã");
            return;
        }

        if (Province == null) {
            sendMessage("Quý khách chưa hoàn thiện thông tin tỉnh/thành phố");
            return;
        }

        if (District == null) {
            sendMessage("Quý khách chưa hoàn thiện thông tin quận/huyện");
            return;
        }

        if (etxt_address_name.getText().toString().equals("")) {
            sendMessage("Quý khách chưa hoàn thiện thông tin tên người nhận");
            return;
        }

        if (etxt_address_phone.getText().toString().equals("")) {
            sendMessage("Quý khách chưa hoàn thiện thông tin số điên thoại");
            return;
        }

        if (etxt_address.getText().toString().equals("")) {
            sendMessage("Quý khách chưa hoàn thiện thông tin đường/số nhà");
            return;
        }

        String is_main = "1";

        if(rg_address.getCheckedRadioButtonId() == R.id.rb_address_company) {
            is_main = "0";
        }

        String title = TITLE;
        String name = etxt_address_name.getText().toString();
        String phone = etxt_address_phone.getText().toString();
        String address = etxt_address.getText().toString();
        int ward_id = Ward.getId();
        int district_id = District.getId();
        int province_id = Province.getId();
        String access_token = homeActivity.getAccessToken();

        AddAddress addAddress = new AddAddress(is_main, title, name, phone, address, ward_id, district_id, province_id, access_token);

        presenter.handleAddAddress(addAddress);
    }

    private void startDialog(List<WardDistrictProvincePersonal> list, TextView textView, String action){
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int height = getActivity().getWindowManager().getDefaultDisplay().getHeight();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog_address_personal, null);
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        window.setLayout((int) Math.round(width * 0.66666666), (int) Math.round(height * 0.66666666));
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initDialog(dialog, view, textView, action, list);
        dialog.show();
    }

    private void initDialog(Dialog dialog, View view, TextView textView, String action, List<WardDistrictProvincePersonal> list) {
        txt_title = view.findViewById(R.id.txt_title);
        rcv_select_address = view.findViewById(R.id.rcv_select_address);

        initViewDialog(dialog, textView, action, list);
    }

    private void initViewDialog(Dialog dialog, TextView textView, String action, List<WardDistrictProvincePersonal> list) {

        AddressDialogAdapter dialogAdapter = new AddressDialogAdapter(list);
        rcv_select_address.setAdapter(dialogAdapter);
        rcv_select_address.setLayoutManager(new LinearLayoutManager(getContext()));

        switch (action) {
            case WARD:
                txt_title.setText("Chọn Phường/Xã");
                break;
            case DISTRICT:
                txt_title.setText("Chọn Quận/Huyện");
                break;
            case PROVINCE:
                txt_title.setText("Chọn Tỉnh/Thành Phố");
                break;
        }

        eventDialog(dialog, textView, dialogAdapter, action);
    }

    private void eventDialog(Dialog dialog, TextView textView, AddressDialogAdapter dialogAdapter, String action) {
        dialogAdapter.setItemOnClickListener(address -> {
            textView.setText(address.getName());
            switch (action) {
                case WARD:
                    Ward = address;
                    break;
                case DISTRICT:
                    District = address;
                    Ward = null;
                    txt_ward.setText("");
                    break;
                case PROVINCE:
                    Province = address;
                    District = null;
                    txt_district.setText("");
                    break;
            }
            dialog.cancel();
        });
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendBroadcast() {
        if (getParentFragmentManager() != null) {
            getParentFragmentManager().popBackStack();
        }

        Intent intent = new Intent(BroadcastUpdatePersonal.ACTION_UPDATE_ADDRESSES);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    @Override
    public void renderAddress(List<WardDistrictProvincePersonal> list, String action, TextView view) {
        startDialog(list, view, action);
    }
}
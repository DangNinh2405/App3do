package com.example.app3do.features.cart.cart_detail.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.constans.Constants;
import com.example.app3do.custom.AddressCartAdapter;
import com.example.app3do.custom.CartDetailAdapter;
import com.example.app3do.features.cart.cart_detail.presenter.CartDetailPresenter;
import com.example.app3do.features.cart.cart_detail.view.CartDetailView;
import com.example.app3do.features.cart.order_confirm.fragment.OrderConfirmFragment;
import com.example.app3do.features.personal_information.new_address_personal_details.fragment.NewAddressPersonalDetailsFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.models.cart.BodyCart;
import com.example.app3do.models.cart.Cart;
import com.example.app3do.models.personal.AddressesPersonal;
import com.example.app3do.models.personal.BodyPersonal;
import com.example.app3do.until.broadcast.BroadcastUpdateCart;
import com.example.app3do.until.broadcast.BroadcastUpdatePersonal;
import com.example.app3do.until.broadcast.UpdatePersonal;
import com.example.app3do.until.direction.Direction;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartDetailFragment extends BaseFragment implements CartDetailView, UpdatePersonal {
    private final String COD = "COD";
    private final String BANKING = "BANKING";
    private final String WALLET = "WALLET";
    HomeActivity homeActivity;
    CartDetailPresenter presenter;
    BroadcastUpdatePersonal receiver;
    private Locale locale;
    private NumberFormat format;
    ImageView img_back;
    List<AddressesPersonal> listAddress;
    AddressesPersonal addressOrder;
    RecyclerView rcv_cart_detail, rcv_addresses_cart;
    CartDetailAdapter cartDetailAdapter;
    AddressCartAdapter addressCartAdapter;
    ScrollView sv_body;
    EditText etxt_search_product, etxt_note;
    boolean isUpdate = false;
    BodyCart cart;
    Button btn_order_confirm;
    RadioGroup rg_discount_type, rg_payment_type;
    TextView txt_add_new_address, txt_no_search_data, txt_no_data, txt_total_product, txt_total_money_origin, txt_total_discount, txt_shipping_fee, txt_total_price, txt_total_point;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cart_detail;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    @Override
    public void onDestroy() {
        rcv_addresses_cart.setAdapter(null);
        rcv_addresses_cart.setLayoutManager(null);
        rcv_addresses_cart.removeAllViews();
        rcv_addresses_cart = null;

        rcv_cart_detail.setAdapter(null);
        rcv_cart_detail.setLayoutManager(null);
        rcv_cart_detail.removeAllViews();
        rcv_cart_detail = null;

        super.onDestroy();
    }

    @Override
    public void onStart() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastUpdatePersonal.ACTION_UPDATE_ADDRESSES);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filter);
        super.onStart();
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        super.onStop();
    }

    private void init(View view) {
        receiver = new BroadcastUpdatePersonal(this);
        homeActivity = (HomeActivity) getActivity();
        presenter = new CartDetailPresenter(this);

        locale = new Locale("vi", "VN");
        format = NumberFormat.getCurrencyInstance(locale);

        rcv_cart_detail = view.findViewById(R.id.rcv_cart_detail);
        img_back = view.findViewById(R.id.img_back);
        txt_total_product = view.findViewById(R.id.txt_total_product);
        txt_total_money_origin = view.findViewById(R.id.txt_total_money_origin);
        txt_total_discount = view.findViewById(R.id.txt_total_discount);
        txt_shipping_fee = view.findViewById(R.id.txt_shipping_fee);
        txt_total_price = view.findViewById(R.id.txt_total_price);
        txt_total_point = view.findViewById(R.id.txt_total_point);
        sv_body = view.findViewById(R.id.sv_body);
        txt_no_data = view.findViewById(R.id.txt_no_data);
        rcv_addresses_cart = view.findViewById(R.id.rcv_addresses_cart);
        txt_add_new_address = view.findViewById(R.id.txt_add_new_address);
        etxt_search_product = view.findViewById(R.id.etxt_search_product);
        txt_no_search_data = view.findViewById(R.id.txt_no_search_data);
        btn_order_confirm = view.findViewById(R.id.btn_order_confirm);
        etxt_note = view.findViewById(R.id.etxt_note);
        rg_discount_type = view.findViewById(R.id.rg_discount_type);
        rg_payment_type = view.findViewById(R.id.rg_payment_type);
    }

    private void initView() {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        presenter.getProductInCart(homeActivity.getAccessToken(), isUpdate);
        presenter.getPersonalInformation(homeActivity.getAccessToken());

        btn_order_confirm.getLayoutParams().width = (int) Math.round(width * 0.85507246376);
        etxt_search_product.requestFocus();
    }

    private void event() {
        img_back.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        txt_add_new_address.setOnClickListener(v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new NewAddressPersonalDetailsFragment(), null, "new_address");
        });

        btn_order_confirm.setOnClickListener(v -> {
            orderConfirm();

        });

        etxt_search_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(etxt_search_product.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void orderConfirm() {
        if (addressOrder == null) {
            Toast.makeText(getContext(), "Chưa có thông tin địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        String note = etxt_note.getText().toString();
        String payment_type = COD;
        String discount_type = "1";
        if (rg_discount_type.getCheckedRadioButtonId() == R.id.rbtn_discount_type_2) {
            discount_type = "2";
        }

        if (rg_payment_type.getCheckedRadioButtonId() == R.id.rbtn_payment_type_banking) {
            payment_type = BANKING;
        } else if (rg_payment_type.getCheckedRadioButtonId() == R.id.rbtn_payment_type_wallet){
            payment_type = WALLET;
        }

        OrderConfirmFragment fragment = new OrderConfirmFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CART, cart);
        bundle.putString(Constants.NOTE, note);
        bundle.putSerializable(Constants.ADDRESS, addressOrder);
        bundle.putString(Constants.DISCOUNT_TYPE, discount_type);
        bundle.putString(Constants.PAYMENT_TYPE, payment_type);
        fragment.setArguments(bundle);

        Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, fragment, null, "cart_detail");

    }

    private void filterList(String text) {
        List<AddressesPersonal> list = new ArrayList<>();
        for (AddressesPersonal item : listAddress) {
            String address = item.getAddress() + ", " + item.getWard().getType() + " " + item.getWard().getName() + ", " + item.getDistrict().getType() + " " + item.getDistrict().getName() + ", " + item.getProvince().getType() + " " + item.getProvince().getName();
            if (address.toLowerCase().contains(text.toLowerCase())) {
                list.add(item);
            }
        }

        if (list.isEmpty()) {
            txt_no_search_data.setVisibility(View.VISIBLE);
        } else {
            txt_no_search_data.setVisibility(View.GONE);
            addressCartAdapter.updateData(list);
            addressCartAdapter.notifyDataSetChanged();
        }

    }

    private void updateProduct(int productId, int quantity, boolean isAddMore) {
        Cart cart = new Cart(productId, quantity, isAddMore);
        presenter.updateProductInCart(homeActivity.getAccessToken(), cart, !isUpdate);
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createViewProductCart(BodyCart bodyCart, boolean isUpdate) {
        if (isUpdate) {
            Intent intent = new Intent(BroadcastUpdateCart.ACTION_UPDATE_CART);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        }

        if (bodyCart != null) {
            cart = bodyCart;
            sv_body.setVisibility(View.VISIBLE);
            txt_no_data.setVisibility(View.GONE);

            txt_total_product.setText(String.valueOf(bodyCart.getMeTaCart().getTotalProduct()));
            txt_total_money_origin.setText(format.format(bodyCart.getMeTaCart().getTotalMoneyOrigin()));
            txt_total_discount.setText(format.format(bodyCart.getMeTaCart().getTotalDiscount() * -1));
            txt_shipping_fee.setText(format.format(bodyCart.getMeTaCart().getShippingFee()));
            txt_total_price.setText(format.format(bodyCart.getMeTaCart().getTotalMoneyOrigin() - bodyCart.getMeTaCart().getTotalDiscount() + bodyCart.getMeTaCart().getShippingFee()));
            txt_total_point.setText(String.valueOf(bodyCart.getMeTaCart().getTotalPoint()));

            int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
            if (cartDetailAdapter == null) {
                cartDetailAdapter = new CartDetailAdapter(width, bodyCart.getDataCart(), false);
                rcv_cart_detail.setAdapter(cartDetailAdapter);
                rcv_cart_detail.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                cartDetailAdapter.updateData(bodyCart.getDataCart());
                cartDetailAdapter.notifyDataSetChanged();
            }


            eventProductCartAdapter(cartDetailAdapter);
        } else {
            sv_body.setVisibility(View.GONE);
            txt_no_data.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void createViewAddressCart(BodyPersonal bodyPersonal) {
        if (bodyPersonal != null) {
            if (listAddress == null) {
                listAddress = new ArrayList<>();
            }

            listAddress = bodyPersonal.getData().getAddresses();
            if (addressCartAdapter == null) {
                addressCartAdapter = new AddressCartAdapter(listAddress);
                rcv_addresses_cart.setAdapter(addressCartAdapter);
                rcv_addresses_cart.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                addressCartAdapter.updateData(listAddress);
                addressCartAdapter.notifyDataSetChanged();
            }

            initAddressOrder();
            eventAddressCartAdapter(addressCartAdapter);
        }
    }

    private void initAddressOrder() {
        if (listAddress.isEmpty()) {
            addressOrder = null;
            return;
        }

        for (AddressesPersonal item : listAddress) {
            if (item.getIsMain() == 1) {
                addressOrder = item;
                break;
            }
        }
    }

    private void eventAddressCartAdapter(AddressCartAdapter adapter) {
        adapter.setItemOnClickListener(new AddressCartAdapter.ItemOnClickListener() {
            @Override
            public void onEdit(AddressesPersonal address) {

            }

            @Override
            public void onClear(AddressesPersonal address) {
                presenter.deleteAddressPersonal(homeActivity.getAccessToken(), address.getId());
            }

            @Override
            public void onCheckedChanged(AddressesPersonal address, int position) {
                int countChild = rcv_addresses_cart.getChildCount();
                for (int i = 0; i <= countChild; i++) {
                    View childView = rcv_addresses_cart.getChildAt(i);
                    if (childView != null) {
                        AddressCartAdapter.MyHolder myHolder = (AddressCartAdapter.MyHolder) rcv_addresses_cart.getChildViewHolder(childView);

                        if (i == position) {
                            addressOrder = address;
                            myHolder.getRbIsMain().setChecked(true);
                        } else {
                            myHolder.getRbIsMain().setChecked(false);
                        }
                    }
                }
            }
        });
    }

    private void eventProductCartAdapter(CartDetailAdapter adapter) {
        adapter.setItemOnClickListener(new CartDetailAdapter.ItemOnClickListener() {
            @Override
            public void minusProduct(int productId, int quantity) {
                updateProduct(productId, quantity, true);
            }

            @Override
            public void plusProduct(int productId, int quantity) {
                updateProduct(productId, quantity, true);
            }

            @Override
            public void deleteProduct(int productId, int quantity) {
                updateProduct(productId, quantity, false);
            }
        });
    }

    @Override
    public void updateAddresses() {
        presenter.getPersonalInformation(homeActivity.getAccessToken());
    }

    @Override
    public void updateBanks() {

    }
}

package com.example.app3do.features.cart.order_confirm.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.constans.Constants;
import com.example.app3do.adapter.CartDetailAdapter;
import com.example.app3do.features.cart.order_confirm.presenter.OrderConfirmPresenter;
import com.example.app3do.features.cart.order_confirm.view.OrderConfirmView;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.models.cart.BodyCart;
import com.example.app3do.models.cart.Order;
import com.example.app3do.models.personal.AddressesPersonal;
import com.example.app3do.utils.broadcast.BroadcastUpdateCart;

import java.text.NumberFormat;
import java.util.Locale;

public class OrderConfirmFragment extends BaseFragment implements OrderConfirmView {
    private final String DISCOUNT_TYPE_1 = "Trừ chiết khấu trực tiếp vào đơn hàng";
    private final String DISCOUNT_TYPE_2 = "Cộng chiết khấu trực tiếp vào điểm tích lũy";
    private final String PAYMENT_TYPE_COD = "Thanh toán khi nhận hàng";
    private final String PAYMENT_TYPE_BANKING = "Thanh toán chuyển khoản";
    private final String PAYMENT_TYPE_WALLET = "Thanh toán qua ví tiêu dùng";
    private final String COD = "COD";
    private final String BANKING = "BANKING";
    private final String WALLET = "WALLET";
    OrderConfirmPresenter presenter;
    HomeActivity homeActivity;
    ImageView img_back;
    BodyCart cart;
    AddressesPersonal address;
    RecyclerView rcv_product_order_confirm;
    String payment_type, discount_type, note;
    Button btn_order_payment;
    EditText etxt_note;
    TextView txt_total_product, txt_total_money_origin, txt_total_discount, txt_shipping_fee, txt_total_price, txt_total_point, txt_discount_type, txt_payment_type, txt_phone_number, txt_name, txt_address;

    private Locale locale;
    private NumberFormat format;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_confirm;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        homeActivity = (HomeActivity) getActivity();
        presenter = new OrderConfirmPresenter(this);

        cart = (BodyCart) getArguments().getSerializable(Constants.CART);
        address = (AddressesPersonal) getArguments().getSerializable(Constants.ADDRESS);
        payment_type = getArguments().getString(Constants.PAYMENT_TYPE);
        discount_type = getArguments().getString(Constants.DISCOUNT_TYPE);
        note = getArguments().getString(Constants.NOTE);

        img_back = view.findViewById(R.id.img_back);
        btn_order_payment = view.findViewById(R.id.btn_order_payment);
        txt_total_product = view.findViewById(R.id.txt_total_product);
        txt_total_money_origin = view.findViewById(R.id.txt_total_money_origin);
        txt_total_discount = view.findViewById(R.id.txt_total_discount);
        txt_shipping_fee = view.findViewById(R.id.txt_shipping_fee);
        txt_total_price = view.findViewById(R.id.txt_total_price);
        txt_total_point = view.findViewById(R.id.txt_total_point);
        txt_payment_type = view.findViewById(R.id.txt_payment_type);
        txt_discount_type = view.findViewById(R.id.txt_discount_type);
        txt_address = view.findViewById(R.id.txt_address);
        txt_phone_number = view.findViewById(R.id.txt_phone_number);
        txt_name = view.findViewById(R.id.txt_name);
        etxt_note = view.findViewById(R.id.etxt_note);
        rcv_product_order_confirm = view.findViewById(R.id.rcv_product_order_confirm);

        locale = new Locale("vi", "VN");
        format = NumberFormat.getCurrencyInstance(locale);
    }

    private void initView() {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        btn_order_payment.getLayoutParams().width = (int) Math.round(width * 0.85507246376);
        if (cart != null) {
            txt_total_product.setText(String.valueOf(cart.getMeTaCart().getTotalProduct()));
            txt_total_money_origin.setText(format.format(cart.getMeTaCart().getTotalMoneyOrigin()));
            txt_total_discount.setText(format.format(cart.getMeTaCart().getTotalDiscount() * -1));
            txt_shipping_fee.setText(format.format(cart.getMeTaCart().getShippingFee()));
            txt_total_price.setText(format.format(cart.getMeTaCart().getTotalMoneyOrigin() - cart.getMeTaCart().getTotalDiscount()));
            txt_total_point.setText(String.valueOf(cart.getMeTaCart().getTotalPoint()));

            CartDetailAdapter cartDetailAdapter = new CartDetailAdapter(width, cart.getDataCart(), true);
            rcv_product_order_confirm.setAdapter(cartDetailAdapter);
            rcv_product_order_confirm.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        if (payment_type != null) {
            switch (payment_type) {
                case COD:
                    txt_payment_type.setText(PAYMENT_TYPE_COD);
                    break;

                case BANKING:
                    txt_payment_type.setText(PAYMENT_TYPE_BANKING);
                    break;

                case WALLET:
                    txt_payment_type.setText(PAYMENT_TYPE_WALLET);
                    break;
            }
        }

        if (discount_type != null) {
            switch (discount_type) {
                case "1":
                    txt_discount_type.setText(DISCOUNT_TYPE_1);
                    break;

                case "2":
                    txt_discount_type.setText(DISCOUNT_TYPE_2);
                    break;
            }
        }

        if (address != null) {
            txt_name.setText(address.getName());
            txt_phone_number.setText(address.getPhone());

            String addressOrder = address.getAddress() + ", " + address.getWard().getType() + " " + address.getWard().getName() + ", " + address.getDistrict().getType() + " " + address.getDistrict().getName() + ", " + address.getProvince().getType() + " " + address.getProvince().getName();
            txt_address.setText(addressOrder);
        }

        if (note != null) {
            etxt_note.setText(note);
            etxt_note.setEnabled(false);
        }

    }

    private void event() {
        img_back.setOnClickListener( v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        btn_order_payment.setOnClickListener( v -> {
            if (address != null) {
                Order order = new Order(address.getName(), address.getPhone(), address.getId(), payment_type, Integer.valueOf(discount_type), note, homeActivity.getAccessToken());
                presenter.handleOrderPayment(order);
            }
        });
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendBroadcastReceiver() {
        Intent intent = new Intent(BroadcastUpdateCart.ACTION_UPDATE_CART);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

        if (getParentFragmentManager() != null) {
            getParentFragmentManager().popBackStackImmediate("cart_detail", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}

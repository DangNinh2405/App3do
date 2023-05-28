package com.example.app3do.features.cart.cart_detail.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.custom.CartDetailAdapter;
import com.example.app3do.features.cart.cart_detail.presenter.CartDetailPresenter;
import com.example.app3do.features.cart.cart_detail.view.CartDetailView;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.models.cart.BodyCart;
import com.example.app3do.models.cart.Cart;
import com.example.app3do.until.broadcast.MyBroadcastReceiver;

import java.text.NumberFormat;
import java.util.Locale;

public class CartDetailFragment extends BaseFragment implements CartDetailView {
    HomeActivity homeActivity;
    CartDetailPresenter presenter;
    private Locale locale;
    private NumberFormat format;
    ImageView img_back;
    RecyclerView rcv_cart_detail;
    ScrollView sv_body;
    LinearLayout lnl_buy;
    TextView txt_no_data, txt_total_product, txt_total_money_origin, txt_total_discount, txt_shipping_fee, txt_total_price, txt_total_point;

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

    private void init(View view) {
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
        lnl_buy = view.findViewById(R.id.lnl_buy);
        txt_no_data = view.findViewById(R.id.txt_no_data);
    }

    private void initView() {
        presenter.getProductInCart(homeActivity.getAccessToken());
    }

    private void event() {
        img_back.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void updateProduct(int productId, int quantity, boolean isAddMore) {
        Cart cart = new Cart(productId, quantity, isAddMore);
        presenter.updateProductInCart(homeActivity.getAccessToken(), cart);
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createViewCart(BodyCart bodyCart) {
        Intent intent = new Intent(MyBroadcastReceiver.ACTION_UPDATE_CART);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        if (bodyCart != null) {
            sv_body.setVisibility(View.VISIBLE);
            lnl_buy.setVisibility(View.VISIBLE);
            txt_no_data.setVisibility(View.GONE);

            txt_total_product.setText(String.valueOf(bodyCart.getMeTaCart().getTotalProduct()));
            txt_total_money_origin.setText(format.format(bodyCart.getMeTaCart().getTotalMoneyOrigin()));
            txt_total_discount.setText(format.format(bodyCart.getMeTaCart().getTotalDiscount() * -1));
            txt_shipping_fee.setText(format.format(bodyCart.getMeTaCart().getShippingFee()));
            txt_total_price.setText(format.format(bodyCart.getMeTaCart().getTotalMoneyOrigin() - bodyCart.getMeTaCart().getTotalDiscount()));
            txt_total_point.setText(String.valueOf(bodyCart.getMeTaCart().getTotalPoint()));

            int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
            CartDetailAdapter cartDetailAdapter = new CartDetailAdapter(width, bodyCart.getDataCart());
            rcv_cart_detail.setAdapter(cartDetailAdapter);
            rcv_cart_detail.setLayoutManager(new LinearLayoutManager(getContext()));
            eventAdapter(cartDetailAdapter);
        } else {
            sv_body.setVisibility(View.GONE);
            lnl_buy.setVisibility(View.GONE);
            txt_no_data.setVisibility(View.VISIBLE);
        }
    }

    private void eventAdapter(CartDetailAdapter adapter) {
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
}

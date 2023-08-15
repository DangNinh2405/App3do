package com.example.app3do.features.product.product_detail.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.text.HtmlCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.constans.Constants;
import com.example.app3do.features.cart.cart_detail.fragment.CartDetailFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.notification.fragment.NotificationFragment;
import com.example.app3do.features.product.product_detail.presenter.ProductDetailPresenter;
import com.example.app3do.features.product.product_detail.view.ProductDetailView;
import com.example.app3do.models.cart.Cart;
import com.example.app3do.models.product.DataProduct;
import com.example.app3do.utils.broadcast.BroadcastNotification;
import com.example.app3do.utils.broadcast.BroadcastUpdateCart;
import com.example.app3do.utils.broadcast.UpdateCart;
import com.example.app3do.utils.broadcast.UpdateNotification;
import com.example.app3do.utils.direction.Direction;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailFragment extends BaseFragment implements ProductDetailView, UpdateCart, UpdateNotification {
    Locale locale;
    NumberFormat format;
    ProductDetailPresenter presenter;
    HomeActivity homeActivity;
    ImageView img_back, img_product;
    TextView txt_product_price, txt_product_name, txt_product_point, txt_product_quantity, txt_product_specifications, txt_product_real_price, txt_quantity;
    DataProduct product;
    BottomSheetDialog dialog;
    BroadcastUpdateCart receiver = new BroadcastUpdateCart(this);
    BroadcastNotification broadcastNotification = new BroadcastNotification(this);

    Button btn_add_to_cart, btn_buy_now;
    boolean isBuyNow = false;

    // BottomSheet
    View viewBottomSheet;
    TextView txt_product_name_bottom, txt_product_price_bottom_1, txt_product_price_bottom_2;
    ImageView img_product_bottom;
    Button btn_minus_product_bottom, btn_plus_product_bottom, btn_add_to_cart_bottom;
    EditText etxt_count_product_bottom;
    RelativeLayout rltl_cart, rltl_notification;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_detail;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    @Override
    public void onStart() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastUpdateCart.ACTION_UPDATE_CART);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filter);

        IntentFilter filterNotification = new IntentFilter();
        filterNotification.addAction(BroadcastNotification.ACTION_UPDATE_NOTIFICATION);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastNotification, filterNotification);
        super.onStart();
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastNotification);
        super.onStop();
    }

    private void init(View view) {
        dialog = new BottomSheetDialog(getContext());
        presenter = new ProductDetailPresenter(this);
        homeActivity = (HomeActivity) getActivity();

        viewBottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.custom_bottom_sheet_dialog_detail_product, null);

        locale = new Locale("vi", "VN");
        format = NumberFormat.getCurrencyInstance(locale);

        img_back = view.findViewById(R.id.img_back);
        img_product = view.findViewById(R.id.img_product);
        txt_product_name = view.findViewById(R.id.txt_product_name);
        txt_product_price = view.findViewById(R.id.txt_product_price);
        txt_product_point = view.findViewById(R.id.txt_product_point);
        txt_product_quantity = view.findViewById(R.id.txt_product_quantity);
        txt_product_specifications = view.findViewById(R.id.txt_product_specifications);
        btn_buy_now = view.findViewById(R.id.btn_buy_now);
        btn_add_to_cart = view.findViewById(R.id.btn_add_to_cart);
        txt_product_real_price = view.findViewById(R.id.txt_product_real_price);
        txt_quantity = view.findViewById(R.id.txt_quantity);
        rltl_cart = view.findViewById(R.id.rltl_cart);
        rltl_notification = view.findViewById(R.id.rltl_notification);

        product = (DataProduct) getArguments().getSerializable(Constants.PRODUCT);
    }


    private void initView() {

        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        img_product.getLayoutParams().height = (int) Math.round(width * 0.88164251207);
        presenter.createQuantityCart(homeActivity.getAccessToken());
        presenter.createQuantifyNotification(homeActivity.getAccessToken());

        if (product != null) {
            Glide.with(getContext()).load(product.getAvatar().getUrl()).into(img_product);
            txt_product_name.setText(product.getName());
            txt_product_price.setText(format.format(product.getPrice()));
            txt_product_point.setText(String.valueOf(product.getPoint()));
            txt_product_quantity.setText(String.valueOf(product.getMinPricePolicy().getQuantity()));
            txt_product_real_price.setText(format.format(product.getMinPricePolicy().getRealPrice()));
            txt_product_specifications.setText(HtmlCompat.fromHtml(product.getSpecifications(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
    }


    private void event() {

        img_back.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        btn_add_to_cart.setOnClickListener(v -> {
            isBuyNow = false;
            addProductToCart();
        });

        btn_buy_now.setOnClickListener(v -> {
            isBuyNow = true;
            addProductToCart();
        });

        rltl_cart.setOnClickListener(v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new CartDetailFragment(), null, "detail_cart");
        });

        rltl_notification.setOnClickListener(v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new NotificationFragment(), null, "notification");
        });
    }

    private void initBottomSheet() {
        img_product_bottom = viewBottomSheet.findViewById(R.id.img_product_bottom);
        etxt_count_product_bottom = viewBottomSheet.findViewById(R.id.etxt_count_product_bottom);
        btn_minus_product_bottom = viewBottomSheet.findViewById(R.id.btn_minus_product_bottom);
        btn_plus_product_bottom = viewBottomSheet.findViewById(R.id.btn_plus_product_bottom);
        btn_add_to_cart_bottom = viewBottomSheet.findViewById(R.id.btn_add_to_cart_bottom);
        txt_product_name_bottom = viewBottomSheet.findViewById(R.id.txt_product_name_bottom);
        txt_product_price_bottom_1 = viewBottomSheet.findViewById(R.id.txt_product_price_bottom_1);
        txt_product_price_bottom_2 = viewBottomSheet.findViewById(R.id.txt_product_price_bottom_2);
    }

    private void initViewBottomSheet() {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        img_product_bottom.getLayoutParams().width = (int) Math.round(width * 0.15700483091);
        img_product_bottom.getLayoutParams().height = (int) Math.round(width * 0.15700483091);
        btn_add_to_cart_bottom.getLayoutParams().width = (int) Math.round(width * 0.88405797101);
        etxt_count_product_bottom.getLayoutParams().width = (int) Math.round(width * 0.36473429951);
        btn_minus_product_bottom.getLayoutParams().width = (int) Math.round(width * 0.14492753623);
        btn_plus_product_bottom.getLayoutParams().width = (int) Math.round(width * 0.14492753623);

        btn_minus_product_bottom.setEnabled(false);

        if (product != null) {
            Glide.with(viewBottomSheet).load(product.getAvatar().getUrl()).into(img_product_bottom);
            txt_product_name_bottom.setText(product.getName());
            txt_product_price_bottom_1.setText(format.format(product.getPrice()));
            txt_product_price_bottom_2.setText(format.format(product.getPrice()));
        }
    }

    private void eventBottomSheet() {
        btn_add_to_cart_bottom.setOnClickListener(v -> {
            int quantity = Integer.valueOf(etxt_count_product_bottom.getText().toString());
            int productId = product.getId();
            Cart cart = new Cart(productId, quantity, true);
            presenter.updateProductInCart(homeActivity.getAccessToken(), cart, isBuyNow);
        });

        btn_minus_product_bottom.setOnClickListener(v -> {
            int count = Integer.valueOf(etxt_count_product_bottom.getText().toString());
            etxt_count_product_bottom.setText(String.valueOf(count - 1));
            count = Integer.valueOf(etxt_count_product_bottom.getText().toString());

            if (count <= 1) {
                v.setEnabled(false);
            }
        });

        btn_plus_product_bottom.setOnClickListener(v -> {
            int count = Integer.valueOf(etxt_count_product_bottom.getText().toString());
            etxt_count_product_bottom.setText(String.valueOf(count + 1));
            count = Integer.valueOf(etxt_count_product_bottom.getText().toString());

            if (count > 1) {
                btn_minus_product_bottom.setEnabled(true);
            }
        });
    }

    private void addProductToCart() {
        dialog.setContentView(viewBottomSheet);
        initBottomSheet();
        initViewBottomSheet();
        eventBottomSheet();
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void createQuantityCart(int quantity) {
        txt_quantity.setText(String.valueOf(quantity));
    }

    @Override
    public void createQuantityNotification(int quantity) {
        txt_quantity.setText(String.valueOf(quantity));
    }

    @Override
    public void sendMessage(String message) {
        Intent intent = new Intent(BroadcastUpdateCart.ACTION_UPDATE_CART);

        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        if (dialog != null) {
            dialog.cancel();
        }
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startCart() {
        Intent intent = new Intent(BroadcastUpdateCart.ACTION_UPDATE_CART);

        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        dialog.cancel();
        Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new CartDetailFragment(), null, "cart_detail");
    }

    @Override
    public void update() {
        presenter.createQuantityCart(homeActivity.getAccessToken());
    }
}

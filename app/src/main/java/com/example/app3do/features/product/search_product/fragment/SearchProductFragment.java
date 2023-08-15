package com.example.app3do.features.product.search_product.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.constans.Constants;
import com.example.app3do.adapter.SeeAllProductHomeAdapter;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.product.product_detail.fragment.ProductDetailFragment;
import com.example.app3do.features.product.search_product.presenter.SearchProductPresenter;
import com.example.app3do.features.product.search_product.view.SearchProductView;
import com.example.app3do.models.cart.Cart;
import com.example.app3do.models.product.DataProduct;
import com.example.app3do.utils.broadcast.BroadcastUpdateCart;
import com.example.app3do.utils.direction.Direction;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SearchProductFragment extends BaseFragment implements SearchProductView {
    SearchProductPresenter presenter;
    Locale locale;
    NumberFormat format;
    HomeActivity homeActivity;
    EditText etxt_search_product;
    TextView txt_result;
    ImageView img_back, img_clear_text;
    ProgressBar pg_loading;
    RecyclerView rcv_search_product;
    GridLayoutManager manager;
    int countProductHorizontal;
    SeeAllProductHomeAdapter seeAllProductHomeAdapter;
    Runnable runnable;
    Handler handler;

    // BottomSheet

    BottomSheetDialog dialog;
    View viewBottomSheet;
    TextView txt_product_name_bottom, txt_product_price_bottom_1, txt_product_price_bottom_2;
    ImageView img_product_bottom;
    Button btn_minus_product_bottom, btn_plus_product_bottom, btn_add_to_cart_bottom;
    EditText etxt_count_product_bottom;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_product;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        presenter = new SearchProductPresenter(this);
        handler = new Handler();

        dialog = new BottomSheetDialog(getContext());
        homeActivity = (HomeActivity) getActivity();
        viewBottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.custom_bottom_sheet_dialog_detail_product, null);

        locale = new Locale("vi", "VN");
        format = NumberFormat.getCurrencyInstance(locale);

        etxt_search_product = view.findViewById(R.id.etxt_search_product);
        img_back = view.findViewById(R.id.img_back);
        img_clear_text = view.findViewById(R.id.img_clear_text);
        pg_loading = view.findViewById(R.id.pg_loading);
        rcv_search_product = view.findViewById(R.id.rcv_search_product);
        txt_result = view.findViewById(R.id.txt_result);
    }

    private void initView() {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        countProductHorizontal = width / Math.round(getResources().getDisplayMetrics().density * 180);
        manager = new GridLayoutManager(getContext(), countProductHorizontal, RecyclerView.VERTICAL, false);

        etxt_search_product.getLayoutParams().width = (int) Math.round(width * 0.92270531401);
    }

    private void event() {
        img_back.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        etxt_search_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    img_clear_text.setVisibility(View.VISIBLE);
                    rcv_search_product.setVisibility(View.GONE);

                    if (runnable == null) {
                        runnable = () -> {
                            presenter.init();
                            presenter.getSearchProductPage(etxt_search_product.getText().toString());
                        };
                    }

                    if (runnable != null) {
                        handler.removeCallbacks(runnable);
                    }

                    isLoading(true);
                    handler.postDelayed(runnable, 2000);

                } else {
                    img_clear_text.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        img_clear_text.setOnClickListener(v -> {
            v.setVisibility(View.GONE);
            etxt_search_product.setText("");
        });

        rcv_search_product.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int countProduct = manager.getChildCount();
                    int indexFirstProduct = manager.findFirstCompletelyVisibleItemPosition();
                    int totalProduct = seeAllProductHomeAdapter.getItemCount();

                    if (totalProduct + countProductHorizontal <= indexFirstProduct + countProduct && totalProduct - countProduct <= indexFirstProduct) {
                        if (pg_loading.getVisibility() == View.GONE) {
                            presenter.getSearchProductPage(etxt_search_product.getText().toString());
                        }
                    }
                }
            }
        });
    }

    private void eventRecycleViewProduct(SeeAllProductHomeAdapter adapter){
        adapter.setItemOnClickListener(new SeeAllProductHomeAdapter.ItemOnClickListener() {
            @Override
            public void onClick(DataProduct product) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.PRODUCT, product);
                ProductDetailFragment detailFragment = new ProductDetailFragment();
                detailFragment.setArguments(bundle);

                Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, detailFragment, null, "detail_product");
            }

            @Override
            public void onClickAddToCart(DataProduct product) {
                addProductToCart(product);
            }
        });
    }

    private void addProductToCart(DataProduct product) {
        dialog.setContentView(viewBottomSheet);
        initBottomSheet();
        initViewBottomSheet(product);
        eventBottomSheet(product);
        dialog.setCancelable(true);
        dialog.show();
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

    private void initViewBottomSheet(DataProduct product) {
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

    private void eventBottomSheet(DataProduct product) {
        btn_add_to_cart_bottom.setOnClickListener(v -> {
            int quantity = Integer.valueOf(etxt_count_product_bottom.getText().toString());
            int productId = product.getId();
            Cart cart = new Cart(productId, quantity, true);
            presenter.updateProductInCart(homeActivity.getAccessToken(), cart);
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

    @Override
    public void isLoading(boolean isLoading) {
        if (isLoading) {
            pg_loading.setVisibility(View.VISIBLE);
        } else {
            pg_loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void createSearchProduct(List<DataProduct> list) {
        if (list.isEmpty()) {
            txt_result.setText("Không có kết quả.");
            rcv_search_product.setVisibility(View.GONE);
            return;
        }

        rcv_search_product.setVisibility(View.VISIBLE);
        txt_result.setText("Kết quả tìm kiếm");
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int scrollPosition = manager.findFirstVisibleItemPosition();
        if(seeAllProductHomeAdapter == null) {
            seeAllProductHomeAdapter = new SeeAllProductHomeAdapter(width, countProductHorizontal, list);
            rcv_search_product.setAdapter(seeAllProductHomeAdapter);
            rcv_search_product.setLayoutManager(manager);
        } else {
            seeAllProductHomeAdapter.updateData(list);
            seeAllProductHomeAdapter.notifyDataSetChanged();
        }

        manager.scrollToPosition(scrollPosition);

        eventRecycleViewProduct(seeAllProductHomeAdapter);
    }

    @Override
    public void sendMessage(String message , boolean isUpdate) {
        if (isUpdate) {
            Intent intent = new Intent(BroadcastUpdateCart.ACTION_UPDATE_CART);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            if (dialog != null) {
                dialog.cancel();
            }
        }
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}

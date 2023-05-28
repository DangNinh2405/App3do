package com.example.app3do.features.product.see_all_product.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.app3do.constans.Category;
import com.example.app3do.constans.Constants;
import com.example.app3do.custom.SeeAllProductHomeAdapter;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.product.product_detail.fragment.ProductDetailFragment;
import com.example.app3do.features.product.see_all_product.presenter.SeeAllProductPresenter;
import com.example.app3do.features.product.see_all_product.view.SeeAllProductView;
import com.example.app3do.models.cart.Cart;
import com.example.app3do.models.home.DataCategory;
import com.example.app3do.models.product.DataProduct;
import com.example.app3do.until.broadcast.MyBroadcastReceiver;
import com.example.app3do.until.direction.Direction;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SeeAllProductFragment extends BaseFragment implements SeeAllProductView {
    Locale locale;
    NumberFormat format;
    SeeAllProductPresenter presenter;
    HomeActivity homeActivity;
    ImageView img_back;
    RecyclerView rcv_all_product;
    ProgressBar pg_loading;
    SeeAllProductHomeAdapter allProductHomeAdapter;
    GridLayoutManager manager;
    int countProductHorizontal;
    Category category;
    DataCategory dataCategory;
    TextView txt_title;

    // bottomSheet
    BottomSheetDialog dialog;
    View viewBottomSheet;
    TextView txt_product_name_bottom, txt_product_price_bottom_1, txt_product_price_bottom_2;
    ImageView img_product_bottom;
    Button btn_minus_product_bottom, btn_plus_product_bottom, btn_add_to_cart_bottom;
    EditText etxt_count_product_bottom;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_see_all_product_home;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        presenter = new SeeAllProductPresenter(this);
        category = (Category) getArguments().getSerializable("ENUM");
        dataCategory = (DataCategory) getArguments().getSerializable("CATEGORY");
        homeActivity = (HomeActivity) getActivity();
        viewBottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.custom_bottom_sheet_dialog_detail_product, null);

        locale = new Locale("vi", "VN");
        format = NumberFormat.getCurrencyInstance(locale);
        dialog = new BottomSheetDialog(getContext());
        img_back = view.findViewById(R.id.img_back);
        rcv_all_product = view.findViewById(R.id.rcv_all_product);
        pg_loading = view.findViewById(R.id.pg_loading);
        txt_title = view.findViewById(R.id.txt_title);
    }

    private void initView() {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        countProductHorizontal = width / Math.round(getResources().getDisplayMetrics().density * 180);
        manager = new GridLayoutManager(getContext(), countProductHorizontal, RecyclerView.VERTICAL, false);

        switch (category) {
            case CATEGORY:
                if (dataCategory != null) {
                    txt_title.setText(dataCategory.getName());
                    presenter.getCategoryProductPage(dataCategory.getId());
                }
                break;
            case HOT:
                txt_title.setText("Sản Phẩm Nội Bật");
                presenter.getHotProductPage();
                break;
            case NEW:
                txt_title.setText("Sản Phẩm Mới");
                presenter.getNewProductPage();
                break;
        }
    }

    private void event() {
        img_back.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        rcv_all_product.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int countProduct = manager.getChildCount();
                    int indexFirstProduct = manager.findFirstCompletelyVisibleItemPosition();
                    int totalProduct = allProductHomeAdapter.getItemCount();

                    if (totalProduct + countProductHorizontal <= indexFirstProduct + countProduct && totalProduct - countProduct <= indexFirstProduct) {
                        if (pg_loading.getVisibility() == View.GONE) {
                            switch (category) {
                                case CATEGORY:
                                    if (dataCategory != null) {
                                        presenter.getCategoryProductPage(dataCategory.getId());
                                    }
                                    break;
                                case HOT:
                                    presenter.getHotProductPage();
                                    break;
                                case NEW:
                                    presenter.getNewProductPage();
                                    break;
                            }
                        }
                    }
                }
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

    @Override
    public void sendMessage(String message, boolean isUpdate) {
        if (isUpdate) {
            Intent intent = new Intent(MyBroadcastReceiver.ACTION_UPDATE_CART);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            if (dialog != null) {
                dialog.cancel();
            }
        }

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createAllProduct(List<DataProduct> list) {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int scrollPosition = manager.findFirstVisibleItemPosition();
        allProductHomeAdapter = new SeeAllProductHomeAdapter(width, countProductHorizontal, list);

        rcv_all_product.setAdapter(allProductHomeAdapter);
        rcv_all_product.setLayoutManager(manager);
        rcv_all_product.setHasFixedSize(true);
        manager.scrollToPosition(scrollPosition);

        eventRecycleViewProduct(allProductHomeAdapter);
    }

    @Override
    public void isLoading(boolean isLoading) {
        if (isLoading) {
            pg_loading.setVisibility(View.VISIBLE);
        } else {
            pg_loading.setVisibility(View.GONE);
        }
    }
}

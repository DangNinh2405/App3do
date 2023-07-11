package com.example.app3do.features.layout.home.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.constans.Category;
import com.example.app3do.constans.Constants;
import com.example.app3do.custom.CategoryHomeAdapter;
import com.example.app3do.custom.ProductHomeAdapter;
import com.example.app3do.custom.SliderHomeAdapter;
import com.example.app3do.features.cart.cart_detail.fragment.CartDetailFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.layout.home.presenter.HomePresenter;
import com.example.app3do.features.layout.home.view.HomeView;
import com.example.app3do.features.notification.fragment.NotificationFragment;
import com.example.app3do.features.product.product_detail.fragment.ProductDetailFragment;
import com.example.app3do.features.product.search_product.fragment.SearchProductFragment;
import com.example.app3do.features.product.see_all_product.fragment.SeeAllProductFragment;
import com.example.app3do.models.home.DataCategory;
import com.example.app3do.models.home.DataSlide;
import com.example.app3do.models.product.DataProduct;
import com.example.app3do.until.broadcast.BroadcastNotification;
import com.example.app3do.until.broadcast.BroadcastUpdateCart;
import com.example.app3do.until.broadcast.UpdateCart;
import com.example.app3do.until.broadcast.UpdateNotification;
import com.example.app3do.until.direction.Direction;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends BaseFragment implements HomeView, UpdateCart, UpdateNotification {
    HomePresenter presenter;
    BroadcastUpdateCart receiver = new BroadcastUpdateCart(this);
    BroadcastNotification broadcastNotification = new BroadcastNotification(this);
    RelativeLayout rltl_cart, rltl_notification;
    ViewPager2 view_pager_slider;
    CircleIndicator3 indicator_slider;
    SliderHomeAdapter sliderHomeAdapter;
    ProductHomeAdapter hotSaleAdapter1, hotSaleAdapter2, newProductAdapter;
    CategoryHomeAdapter categoryHomeAdapter;
    RecyclerView rcv_category, rcv_hot_sale_1, rcv_hot_sale_2, rcv_new_product;
    TextView see_all_new_product, see_all_hot_sale, txt_search_product, txt_quantity_cart, txt_quantity_notification;
    HomeActivity homeActivity;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    @Override
    public void onStart() {
        IntentFilter filterCart = new IntentFilter();
        filterCart.addAction(BroadcastUpdateCart.ACTION_UPDATE_CART);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filterCart);

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
        homeActivity = (HomeActivity) getActivity();
        presenter = new HomePresenter(this);

        view_pager_slider = view.findViewById(R.id.view_pager_slider);
        indicator_slider = view.findViewById(R.id.indicator_slider);
        rcv_category = view.findViewById(R.id.rcv_category);
        rcv_hot_sale_1 = view.findViewById(R.id.rcv_hot_sale_1);
        rcv_hot_sale_2 = view.findViewById(R.id.rcv_hot_sale_2);
        rcv_new_product = view.findViewById(R.id.rcv_new_product);
        see_all_new_product = view.findViewById(R.id.see_all_new_product);
        see_all_hot_sale = view.findViewById(R.id.see_all_hot_sale);
        txt_search_product = view.findViewById(R.id.txt_search_product);
        rltl_cart = view.findViewById(R.id.rltl_cart);
        txt_quantity_cart = view.findViewById(R.id.txt_quantity_cart);
        txt_quantity_notification = view.findViewById(R.id.txt_quantity_notification);
        rltl_notification = view.findViewById(R.id.rltl_notification);
    }

    private void initView() {
        presenter.createSlider();
        presenter.createCategory();
        presenter.createHotSaleProduct(Constants.PAGE_1, true);
        presenter.createHotSaleProduct(Constants.PAGE_2, false);
        presenter.createNewProduct(Constants.PAGE_1);
        presenter.createQuantityCart(homeActivity.getAccessToken());
        presenter.createQuantifyNotification(homeActivity.getAccessToken());
    }

    private void event() {
        see_all_hot_sale.setOnClickListener(v -> {
            directionFragment(Category.HOT, null);
        });

        see_all_new_product.setOnClickListener(v -> {
            directionFragment(Category.NEW, null);
        });

        txt_search_product.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new SearchProductFragment(), null, "search_product");
        });

        rltl_cart.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new CartDetailFragment(), null, "cart_detail");
        });

        rltl_notification.setOnClickListener( v -> {
            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new NotificationFragment(), null, "notification");
        });
    }

    private void directionFragment(Category enumCategory, DataCategory category) {
        SeeAllProductFragment fragment = new SeeAllProductFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ENUM", enumCategory);
        bundle.putSerializable("CATEGORY", category);
        fragment.setArguments(bundle);

        Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, fragment, null, "see_all_product");
    }

    @Override
    public void createSlider(List<DataSlide> list) {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        sliderHomeAdapter = new SliderHomeAdapter(list, width);
        view_pager_slider.setAdapter(sliderHomeAdapter);
        indicator_slider.setViewPager(view_pager_slider);

        sliderHomeAdapter.registerAdapterDataObserver(indicator_slider.getAdapterDataObserver());
    }

    @Override
    public void getMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createHotSaleOne(List<DataProduct> list) {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        hotSaleAdapter1 = new ProductHomeAdapter(width, list);
        rcv_hot_sale_1.setAdapter(hotSaleAdapter1);
        rcv_hot_sale_1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        eventRecycleViewProduct(hotSaleAdapter1);
    }

    @Override
    public void createHotSaleTwo(List<DataProduct> list) {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        hotSaleAdapter2 = new ProductHomeAdapter(width, list);
        rcv_hot_sale_2.setAdapter(hotSaleAdapter2);
        rcv_hot_sale_2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        eventRecycleViewProduct(hotSaleAdapter2);
    }

    @Override
    public void createNewProduct(List<DataProduct> list) {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        newProductAdapter = new ProductHomeAdapter(width, list);
        rcv_new_product.setAdapter(newProductAdapter);
        rcv_new_product.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        eventRecycleViewProduct(newProductAdapter);
    }

    @Override
    public void createCategory(List<DataCategory> list) {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        categoryHomeAdapter = new CategoryHomeAdapter(width, list);
        rcv_category.setAdapter(categoryHomeAdapter);
        rcv_category.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        eventCategory(categoryHomeAdapter);
    }

    @Override
    public void createQuantityCart(int quantity) {
        txt_quantity_cart.setText(String.valueOf(quantity));
    }

    @Override
    public void createQuantifyNotification(int quantity) {
        txt_quantity_notification.setText(String.valueOf(quantity));
    }

    private void eventRecycleViewProduct(ProductHomeAdapter adapter){
        adapter.setItemOnClickListener(product -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.PRODUCT, product);
            ProductDetailFragment detailFragment = new ProductDetailFragment();
            detailFragment.setArguments(bundle);

            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, detailFragment, null, "detail_product");
        });
    }

    private void eventCategory(CategoryHomeAdapter adapter){
        adapter.setItemOnClickListener(category -> {
            directionFragment(Category.CATEGORY, category);
        });
    }

    @Override
    public void update() {
        presenter.createQuantityCart(homeActivity.getAccessToken());
        presenter.createQuantifyNotification(homeActivity.getAccessToken());
    }

}

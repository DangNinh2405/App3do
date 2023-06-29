package com.example.app3do.features.order.personal.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.custom.OrdersAdapter;
import com.example.app3do.features.cart.cart_detail.fragment.CartDetailFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.order.personal.presenter.PersonalOrdersPresenter;
import com.example.app3do.features.order.personal.view.PersonalOrdersView;
import com.example.app3do.models.order.DataOrder;
import com.example.app3do.until.broadcast.BroadcastOrder;
import com.example.app3do.until.broadcast.BroadcastUpdateCart;
import com.example.app3do.until.broadcast.UpdateOrders;
import com.example.app3do.until.direction.Direction;

import java.util.Calendar;
import java.util.List;

public class PersonalOrdersFragment extends BaseFragment implements PersonalOrdersView, UpdateOrders {
    PersonalOrdersPresenter presenter;
    HomeActivity homeActivity;
    BroadcastOrder receiver = new BroadcastOrder(this);

    Button btn_this_month, btn_last_month, btn_this_year;
    LinearLayout lnl_select, lnl_select_time;
    TextView txt_time_before, txt_time_after, txt_no_data;
    RelativeLayout rltl_select_time;
    RecyclerView rcv_orders;
    OrdersAdapter adapter;
    ProgressBar pg_loading;
    String startDate = "";
    String endDate = "";
    boolean isSelectStartDate = false;
    boolean isSelectEndDate = false;

    LinearLayoutManager manager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_orders;
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
        filter.addAction(BroadcastOrder.ACTION_UPDATE_ORDERS);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filter);
        super.onStart();
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        super.onStop();
    }

    private void init(View view) {
        presenter = new PersonalOrdersPresenter(this);
        homeActivity = (HomeActivity) getActivity();

        btn_this_month = view.findViewById(R.id.btn_this_month);
        btn_last_month = view.findViewById(R.id.btn_last_month);
        btn_this_year = view.findViewById(R.id.btn_this_year);
        lnl_select = view.findViewById(R.id.lnl_select);
        lnl_select_time = view.findViewById(R.id.lnl_select_time);
        txt_time_before = view.findViewById(R.id.txt_time_before);
        txt_time_after = view.findViewById(R.id.txt_time_after);
        rltl_select_time = view.findViewById(R.id.rltl_select_time);
        pg_loading = view.findViewById(R.id.pg_loading);
        rcv_orders = view.findViewById(R.id.rcv_orders);
        txt_no_data = view.findViewById(R.id.txt_no_data);

        manager = new LinearLayoutManager(getContext());
    }

    private void initView() {
        thisMonthOrder();
    }

    private void event() {
        btn_this_month.setOnClickListener(v -> {
            setDefaultView();
            setColorButton((Button) v);

            thisMonthOrder();
        });

        btn_last_month.setOnClickListener(v -> {
            setDefaultView();
            setColorButton((Button) v);
            lastMonthOrder();
        });

        btn_this_year.setOnClickListener(v -> {
            setDefaultView();
            setColorButton((Button) v);

            thisYear();
        });

        rltl_select_time.setOnClickListener(v -> {
            setDefaultView();
            v.setBackgroundResource(R.drawable.custom_btn_operation_focus);
            lnl_select.setVisibility(View.GONE);
            lnl_select_time.setVisibility(View.VISIBLE);
        });

        txt_time_before.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                String time = dayOfMonth + "/" + (month + 1) + "/" + year;
                startDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                isSelectStartDate = true;
                txt_time_before.setText(time);
                if (isSelectStartDate && isSelectEndDate) {
                    presenter.createOrder(homeActivity.getAccessToken(), startDate, endDate);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
        });

        txt_time_after.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                String time = dayOfMonth + "/" + (month + 1) + "/" + year;
                txt_time_after.setText(time);
                endDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                isSelectEndDate = true;
                if (isSelectStartDate && isSelectEndDate) {
                    presenter.createOrder(homeActivity.getAccessToken(), startDate, endDate);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
        });

        rcv_orders.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int countProduct = manager.getChildCount();
                    int indexFirstProduct = manager.findFirstCompletelyVisibleItemPosition();
                    int totalProduct = adapter.getItemCount();

                    if (totalProduct + 1 <= indexFirstProduct + countProduct && totalProduct - countProduct <= indexFirstProduct) {
                        if (pg_loading.getVisibility() == View.GONE) {
                            presenter.createOrder(homeActivity.getAccessToken(), startDate, endDate);
                        }
                    }
                }
            }
        });

    }

    private void thisMonthOrder() {
        txt_no_data.setVisibility(View.GONE);
        presenter.init();

        Calendar calendar = Calendar.getInstance();
        startDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        endDate = "" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        presenter.createOrder(homeActivity.getAccessToken(), startDate, endDate);
    }

    private void lastMonthOrder() {
        txt_no_data.setVisibility(View.GONE);
        presenter.init();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        startDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        endDate = "" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        presenter.createOrder(homeActivity.getAccessToken(), startDate, endDate);
    }

    private void thisYear() {
        txt_no_data.setVisibility(View.GONE);
        presenter.init();
        Calendar calendar = Calendar.getInstance();
        startDate = "01" + "-" + "01" + "-" + calendar.get(Calendar.YEAR);
        endDate = "" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        presenter.createOrder(homeActivity.getAccessToken(), startDate, endDate);
    }

    private void setDefaultView() {
        isSelectEndDate = false;
        isSelectStartDate = false;
        btn_this_month.setBackgroundResource(R.drawable.custom_btn_operation_unfocus);
        btn_last_month.setBackgroundResource(R.drawable.custom_btn_operation_unfocus);
        btn_this_year.setBackgroundResource(R.drawable.custom_btn_operation_unfocus);
        rltl_select_time.setBackgroundResource(R.drawable.custom_btn_operation_unfocus);

        btn_this_month.setTextColor(Color.rgb(33, 49, 64));
        btn_last_month.setTextColor(Color.rgb(33, 49, 64));
        btn_this_year.setTextColor(Color.rgb(33, 49, 64));
    }

    private void setColorButton(Button v) {
        v.setBackgroundResource(R.drawable.custom_btn_operation_focus);
        v.setTextColor(Color.rgb(255, 255, 255));
        lnl_select.setVisibility(View.VISIBLE);
        lnl_select_time.setVisibility(View.GONE);
        txt_time_before.setText("Chọn thời gian");
        txt_time_after.setText("Chọn thời gian");
    }

    @Override
    public void sendMessage(String message, boolean sendBroadcast) {
        if (sendBroadcast) {
            Intent intentOrder = new Intent(BroadcastOrder.ACTION_UPDATE_ORDERS);
            Intent intentCart = new Intent(BroadcastUpdateCart.ACTION_UPDATE_CART);

            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intentOrder);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intentCart);
        }

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createOrdersView(List<DataOrder> order) {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        if (order.isEmpty()) {
            txt_no_data.setVisibility(View.VISIBLE);
            rcv_orders.setVisibility(View.GONE);
            return;
        }

        txt_no_data.setVisibility(View.GONE);
        rcv_orders.setVisibility(View.VISIBLE);

        if (adapter == null) {
            adapter = new OrdersAdapter(width, order);
            rcv_orders.setAdapter(adapter);
            rcv_orders.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            adapter.updateData(order);
            adapter.notifyDataSetChanged();
        }

        adapter.setItemOnClickListener(new OrdersAdapter.ItemOnClickListener() {
            @Override
            public void onClickCancel(DataOrder order) {
                presenter.cancelOrder(order.getId(), homeActivity.getAccessToken());
            }

            @Override
            public void onClickOrderAgain(DataOrder order) {
                presenter.orderAgain(order.getId(), homeActivity.getAccessToken(), order);
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
    public void openCart() {
        Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, new CartDetailFragment(), null, "cart_detail");
    }

    @Override
    public void update() {
        presenter.createOrder(homeActivity.getAccessToken(), startDate, endDate);
    }
}

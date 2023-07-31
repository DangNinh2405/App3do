package com.example.app3do.features.layout.marketing.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.custom.MemberMarketingAdapter;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.layout.marketing.presenter.MarketingPresenter;
import com.example.app3do.features.layout.marketing.view.MarketingView;
import com.example.app3do.models.marketing.MetaMarketing;
import com.example.app3do.models.personal.DataPersonal;
import com.example.app3do.models.report.PointReports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MarketingFragment extends BaseFragment implements MarketingView {
    MarketingPresenter presenter;
    HomeActivity homeActivity;
    TextView txt_time_before, txt_time_after;
    List<DataPersonal> list;
    Spinner sn_sort;
    String sort = "level";
    Button btn_this_month, btn_last_month, btn_this_year;
    RelativeLayout rltl_select_time;
    LinearLayout lnl_select, lnl_select_time;
    boolean isSelectEndDate = false;
    boolean isSelectStartDate = false;
    boolean isGirdView = false;
    boolean rcvIsGone = false;
    RecyclerView rcv_nember_f1;
    ImageView img_list;
    TextView txt_total_point, txt_total_point_f1, txt_total_money_member_report, txt_total_money_member_f1, txt_countGroupOnly, txt_moneyGroupChi;
    String startDate = "";
    String endDate = "";
    ProgressBar pg_loading;
    EditText etxt_search;
    GridLayoutManager managerGird;
    LinearLayoutManager managerLinear;
    int countProductHorizontal;

    MemberMarketingAdapter adapter;
    int width;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_marketing;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        presenter = new MarketingPresenter(this);
        homeActivity = (HomeActivity) getActivity();

        txt_time_before = view.findViewById(R.id.txt_time_before);
        txt_time_after = view.findViewById(R.id.txt_time_after);
        btn_this_month = view.findViewById(R.id.btn_this_month);
        btn_last_month = view.findViewById(R.id.btn_last_month);
        btn_this_year = view.findViewById(R.id.btn_this_year);
        rltl_select_time = view.findViewById(R.id.rltl_select_time);
        lnl_select = view.findViewById(R.id.lnl_select);
        lnl_select_time = view.findViewById(R.id.lnl_select_time);
        sn_sort = view.findViewById(R.id.sn_sort);
        txt_total_point = view.findViewById(R.id.txt_total_point);
        txt_total_point_f1 = view.findViewById(R.id.txt_total_point_f1);
        txt_total_money_member_report = view.findViewById(R.id.txt_total_money_member_report);
        txt_total_money_member_f1 = view.findViewById(R.id.txt_total_money_member_f1);
        txt_countGroupOnly = view.findViewById(R.id.txt_countGroupOnly);
        txt_moneyGroupChi = view.findViewById(R.id.txt_moneyGroupChi);
        rcv_nember_f1 = view.findViewById(R.id.rcv_nember_f1);
        img_list = view.findViewById(R.id.img_list);
        etxt_search = view.findViewById(R.id.etxt_search);
        pg_loading = view.findViewById(R.id.pg_loading);

        Calendar calendar = Calendar.getInstance();
        startDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        endDate = calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);

        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        countProductHorizontal = width / Math.round(getResources().getDisplayMetrics().density * 180);
        managerGird = new GridLayoutManager(getContext(), countProductHorizontal, RecyclerView.VERTICAL, false);
        managerLinear = new LinearLayoutManager(getContext());
    }

    private void initView() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.sort_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sn_sort.setAdapter(adapter);
        presenter.createMemberMarketingF1(homeActivity.getAccessToken(), sort, startDate, endDate, !rcvIsGone);
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
            thisYearOrder();
        });

        rltl_select_time.setOnClickListener(v -> {
            setDefaultView();
            v.setBackgroundResource(R.drawable.custom_btn_operation_focus);
            lnl_select.setVisibility(View.GONE);
            lnl_select_time.setVisibility(View.VISIBLE);
        });

        txt_time_before.setOnClickListener(v -> {
            startDatePicker(txt_time_before, true);
        });

        txt_time_after.setOnClickListener(v -> {
            startDatePicker(txt_time_after, false);
        });

        img_list.setOnClickListener(v -> {
            handleRenderGirdView();
        });

        etxt_search.addTextChangedListener(searchTextChange(etxt_search));


        rcv_nember_f1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadPage(dy);
            }
        });
        sn_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectSort(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void selectSort(int position) {
        switch (position) {
            case 0:
                sort = "level";
                break;
            case 1:
                sort = "direct";
                break;
            case 2:
                sort = "point";
                break;
        }

        presenter.createMemberMarketingF1(homeActivity.getAccessToken(), sort, startDate, endDate, !rcvIsGone);
    }

    private void loadPage(int dy) {
        if (dy > 0) {
            int countMember;
            int indexFirstMember;
            int totalMember;
            int countMemberF1;

            if (isGirdView) {
                countMemberF1 = countProductHorizontal;
                countMember = managerGird.getChildCount();
                indexFirstMember = managerGird.findFirstCompletelyVisibleItemPosition();
                totalMember = adapter.getItemCount();
            } else {
                countMemberF1 = 1;
                countMember = managerLinear.getChildCount();
                indexFirstMember = managerLinear.findFirstCompletelyVisibleItemPosition();
                totalMember = adapter.getItemCount();
            }

            if (totalMember + countMemberF1 <= indexFirstMember + countMember && totalMember - countMember <= indexFirstMember) {
                if (pg_loading.getVisibility() == View.GONE) {
                    presenter.createMemberMarketingF1(homeActivity.getAccessToken(), sort, startDate, endDate, rcvIsGone);
                }
            }
        }
    }

    private TextWatcher searchTextChange(TextView textView) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(textView.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private void filterList(String text) {
        List<DataPersonal> searchList = new ArrayList<>();

        for (DataPersonal item : list) {
            String textContent = item.getName() + item.getPhone();
            if (textContent.toLowerCase().contains(text.toLowerCase())) {
                searchList.add(item);
            }
        }

        adapter.updateData(searchList);
        adapter.notifyDataSetChanged();
    }

    private void handleRenderGirdView() {
        if (isGirdView) {
            img_list.setImageResource(R.drawable.list_grid_2x);
            isGirdView = false;
        } else {
            img_list.setImageResource(R.drawable.list_linear_2x);
            isGirdView = true;
        }

        adapter.updateLayoutManager(isGirdView);
        setLayoutRecycleViewMember(isGirdView);
    }

    private void startDatePicker(TextView textView, boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            String time = dayOfMonth + "/" + (month + 1) + "/" + year;
            textView.setText(time);

            if (isStartDate) {
                startDate = dayOfMonth + "-" + (month + 1) + "-" + year;
            } else {
                endDate = dayOfMonth + "-" + (month + 1) + "-" + year;
            }

            isSelectEndDate = true;
            if (isSelectStartDate && isSelectEndDate) {

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    private void thisMonthOrder() {
        Calendar calendar = Calendar.getInstance();
        startDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        endDate = "" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        presenter.init();
        presenter.createMemberMarketingF1(homeActivity.getAccessToken(), sort, startDate, endDate, !rcvIsGone);
    }

    private void lastMonthOrder() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        startDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        endDate = "" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        presenter.init();
        presenter.createMemberMarketingF1(homeActivity.getAccessToken(), sort, startDate, endDate, !rcvIsGone);
    }

    private void thisYearOrder() {
        Calendar calendar = Calendar.getInstance();
        startDate = "01" + "-" + "01" + "-" + calendar.get(Calendar.YEAR);
        endDate = "" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        presenter.init();
        presenter.createMemberMarketingF1(homeActivity.getAccessToken(), sort, startDate, endDate, !rcvIsGone);
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

    private void setLayoutRecycleViewMember(boolean isGird) {
        adapter = new MemberMarketingAdapter(width, countProductHorizontal, isGirdView, list);
        rcv_nember_f1.setAdapter(adapter);
        if (isGird) {
            rcv_nember_f1.setLayoutManager(managerGird);
        } else {
            rcv_nember_f1.setLayoutManager(managerLinear);
        }
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createMemberMarketingView(PointReports point, List<DataPersonal> dataList) {
        if (dataList != null) {
            list = dataList;
            if (adapter == null) {
                adapter = new MemberMarketingAdapter(width, countProductHorizontal, isGirdView, dataList);
                rcv_nember_f1.setAdapter(adapter);
                if (isGirdView) {
                    rcv_nember_f1.setLayoutManager(managerGird);
                } else {
                    rcv_nember_f1.setLayoutManager(managerLinear);
                }

            } else {
                adapter.updateData(dataList);
                adapter.notifyDataSetChanged();
            }
        }


        if (point != null) {
            txt_total_money_member_f1.setText(String.valueOf(point.getTotalMoneyMemberF1()));
            txt_total_point_f1.setText(String.valueOf(point.getTotalPointF1()));
            txt_moneyGroupChi.setText(String.valueOf(point.getMoneyGroupChi()));
            txt_countGroupOnly.setText(String.valueOf(point.getCountGroupOnly()));
            txt_total_money_member_report.setText(String.valueOf(point.getTotalMoneyMemberReport()));
            txt_total_point.setText(String.valueOf(point.getTotalPoint()));
        }


    }

    @Override
    public void loading(boolean isLoading, boolean rcvIsGone) {
        if (isLoading) {
            pg_loading.setVisibility(View.VISIBLE);
            if (rcvIsGone) {
                rcv_nember_f1.setVisibility(View.GONE);
            } else {
                rcv_nember_f1.setVisibility(View.VISIBLE);
            }
        } else {
            pg_loading.setVisibility(View.GONE);
            rcv_nember_f1.setVisibility(View.VISIBLE);
        }
    }
}

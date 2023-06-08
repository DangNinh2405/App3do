package com.example.app3do.features.layout.report.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.layout.report.presenter.ReportPresenter;
import com.example.app3do.features.layout.report.view.ReportView;
import com.example.app3do.models.report.DataReports;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReportFragment extends BaseFragment implements ReportView {
    ReportPresenter presenter;
    HomeActivity homeActivity;
    Button btn_this_month, btn_last_month, btn_this_year;
    RelativeLayout rltl_select_time;
    ProgressBar pg_loading;
    LinearLayout lnl_select, lnl_select_time, lnl_date, lnl_body;
    TextView txt_time_before, txt_time_after, txt_total_point, txt_total_point_member, txt_total_user, txt_total_money_member_f1, txt_start_date
            ,txt_total_point_f1, txt_moneyGroupChi, txt_moneyCommissionGroupOnly, txt_moneyGroupOnly, txt_countGroupOnly, txt_end_date
            , txt_total_money_member_report, txt_all_order, txt_all_money, txt_owner_order, txt_owner_money, txt_direct_order, txt_direct_money;
    boolean isSelectStartDate = false;
    boolean isSelectEndDate = false;
    String startDate = "";
    String endDate = "";
    @Override
    public int getLayoutId() {
        return R.layout.fragment_report;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        event();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void init(View view ) {
        presenter = new ReportPresenter(this);
        homeActivity = (HomeActivity) getActivity();

        lnl_body = view.findViewById(R.id.lnl_body);
        pg_loading = view.findViewById(R.id.pg_loading);
        btn_this_month = view.findViewById(R.id.btn_this_month);
        btn_last_month = view.findViewById(R.id.btn_last_month);
        btn_this_year = view.findViewById(R.id.btn_this_year);
        rltl_select_time = view.findViewById(R.id.rltl_select_time);
        lnl_select = view.findViewById(R.id.lnl_select);
        lnl_select_time = view.findViewById(R.id.lnl_select_time);
        txt_time_before = view.findViewById(R.id.txt_time_before);
        txt_time_after = view.findViewById(R.id.txt_time_after);
        lnl_date = view.findViewById(R.id.lnl_date);
        txt_total_user = view.findViewById(R.id.txt_total_user);
        txt_total_money_member_f1 = view.findViewById(R.id.txt_total_money_member_f1);
        txt_total_point_f1 = view.findViewById(R.id.txt_total_point_f1);
        txt_moneyGroupChi = view.findViewById(R.id.txt_moneyGroupChi);
        txt_moneyCommissionGroupOnly = view.findViewById(R.id.txt_moneyCommissionGroupOnly);
        txt_moneyGroupOnly = view.findViewById(R.id.txt_moneyGroupOnly);
        txt_countGroupOnly = view.findViewById(R.id.txt_countGroupOnly);
        txt_total_money_member_report = view.findViewById(R.id.txt_total_money_member_report);
        txt_total_point = view.findViewById(R.id.txt_total_point);
        txt_total_point_member = view.findViewById(R.id.txt_total_point_member);
        txt_all_order = view.findViewById(R.id.txt_all_order);
        txt_all_money = view.findViewById(R.id.txt_all_money);
        txt_start_date = view.findViewById(R.id.txt_start_date);
        txt_end_date = view.findViewById(R.id.txt_end_date);
        txt_owner_order = view.findViewById(R.id.txt_owner_order);
        txt_owner_money = view.findViewById(R.id.txt_owner_money);
        txt_direct_order = view.findViewById(R.id.txt_direct_order);
        txt_direct_money = view.findViewById(R.id.txt_direct_money);

        Calendar calendar = Calendar.getInstance();
        startDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        endDate = calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
    }

    private void initView() {
        presenter.getReports(homeActivity.getAccessToken(), startDate, endDate);
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
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                String time = dayOfMonth + "/" + (month + 1) + "/" + year;
                txt_time_before.setText(time);

                startDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                isSelectStartDate = true;
                if (isSelectStartDate && isSelectEndDate) {
                    presenter.getReports(homeActivity.getAccessToken(), startDate, endDate);
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
                    presenter.getReports(homeActivity.getAccessToken(), startDate, endDate);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
        });
    }

    private void setDefaultView() {
        isSelectEndDate = false;
        isSelectStartDate = false;
        lnl_date.setVisibility(View.GONE);
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

    private void thisMonthOrder() {
        Calendar calendar = Calendar.getInstance();
        startDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        endDate = "" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        presenter.getReports(homeActivity.getAccessToken(), startDate, endDate);
    }

    private void lastMonthOrder() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        startDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        endDate = "" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        presenter.getReports(homeActivity.getAccessToken(), startDate, endDate);
    }

    private void thisYearOrder() {
        Calendar calendar = Calendar.getInstance();
        startDate = "01" + "-" + "01" + "-" + calendar.get(Calendar.YEAR);
        endDate = "" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        presenter.getReports(homeActivity.getAccessToken(), startDate, endDate);
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createViewReports(DataReports reports) {
        if (reports != null) {
            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);

            lnl_date.setVisibility(View.VISIBLE);

            txt_total_user.setText(String.valueOf(reports.getUsers().getTotal()));
            txt_total_money_member_f1.setText(String.valueOf(reports.getPoint().getTotalMoneyMemberF1()));
            txt_total_point_f1.setText(String.valueOf(reports.getPoint().getTotalPointF1()));
            txt_moneyGroupChi.setText(String.valueOf(reports.getPoint().getMoneyGroupChi()));
            txt_moneyCommissionGroupOnly.setText(String.valueOf(reports.getPoint().getMoneyCommissionGroupOnly()));
            txt_moneyGroupOnly.setText(String.valueOf(reports.getPoint().getMoneyGroupOnly()));
            txt_countGroupOnly.setText(String.valueOf(reports.getPoint().getCountGroupOnly()));
            txt_total_money_member_report.setText(String.valueOf(reports.getPoint().getTotalMoneyMemberReport()));
            txt_total_point.setText(String.valueOf(reports.getPoint().getTotalPoint()));
            txt_total_point_member.setText(String.valueOf(reports.getPoint().getTotalMoneyMember()));

            txt_all_order.setText(reports.getOrder().getAll().getTotal() + " Đơn");
            txt_all_money.setText(format.format(reports.getOrder().getAll().getMoney()));
            txt_owner_order.setText(reports.getOrder().getOwner().getTotal()+ " Đơn");
            txt_owner_money.setText(format.format(reports.getOrder().getOwner().getMoney()));
            txt_direct_order.setText(reports.getOrder().getDirect().getTotal()+ " Đơn");
            txt_direct_money.setText(format.format(reports.getOrder().getDirect().getMoney()));

            txt_end_date.setText(endDate);
            txt_start_date.setText(startDate);
        }
    }

    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            pg_loading.setVisibility(View.VISIBLE);
            lnl_body.setVisibility(View.GONE);
        } else {
            pg_loading.setVisibility(View.GONE);
            lnl_body.setVisibility(View.VISIBLE);
        }
    }
}

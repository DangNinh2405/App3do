package com.example.app3do.features.notification.fragment;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.custom.NotificationAdapter;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.notification.presenter.NotificationPresenter;
import com.example.app3do.features.notification.view.NotificationView;
import com.example.app3do.models.notification.DataNotification;
import com.example.app3do.until.broadcast.BroadcastNotification;

import java.util.List;


public class NotificationFragment extends BaseFragment implements NotificationView {
    NotificationPresenter presenter;
    HomeActivity homeActivity;
    ImageView img_back;
    RecyclerView rcv_notifications;
    NotificationAdapter adapter;
    ProgressBar pg_loading;
    LinearLayoutManager manager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notification;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        homeActivity = (HomeActivity) getActivity();
        presenter = new NotificationPresenter(this);

        manager = new LinearLayoutManager(getContext());

        img_back = view.findViewById(R.id.img_back);
        rcv_notifications = view.findViewById(R.id.rcv_notifications);
        pg_loading = view.findViewById(R.id.pg_loading);
    }

    private void initView() {
        presenter.createNotificationView(homeActivity.getAccessToken());
    }

    private void event() {
        img_back.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        rcv_notifications.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int count= manager.getChildCount();
                    int indexFirst = manager.findFirstCompletelyVisibleItemPosition();
                    int total = adapter.getItemCount();

                    if (total + 1 <= indexFirst + count && total - count <= indexFirst) {
                        if (pg_loading.getVisibility() == View.GONE) {
                            presenter.createNotificationView(homeActivity.getAccessToken());
                        }
                    }
                }
            }
        });


    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            pg_loading.setVisibility(View.VISIBLE);
        } else {
            pg_loading.setVisibility(View.GONE);
        }
    }


    @Override
    public void createNotificationView(List<DataNotification> data) {
        if (adapter == null) {
            adapter = new NotificationAdapter(data);
            rcv_notifications.setAdapter(adapter);
            rcv_notifications.setLayoutManager(manager);
        } else {
            adapter.updateData(data);
            adapter.notifyDataSetChanged();
        }

        Intent intent = new Intent(BroadcastNotification.ACTION_UPDATE_NOTIFICATION);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }
}

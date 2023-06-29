package com.example.app3do.custom;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.models.notification.DataNotification;

import java.sql.Time;
import java.time.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyHolder>{
    private List<DataNotification> list;

    public void updateData(List<DataNotification> list) {
        this.list = list;
    }

    public NotificationAdapter(List<DataNotification> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_notification, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DataNotification notification = list.get(position);

        if (notification != null) {
            holder.txt_content.setText(notification.getContent());
            holder.txt_title.setText(notification.getTitle());
            String[] date = notification.getCreateAt().getDate().split(" ");

            holder.txt_date.setText(date[0]);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView txt_date, txt_content, txt_title;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_content = itemView.findViewById(R.id.txt_content);
            txt_title = itemView.findViewById(R.id.txt_title);
        }
    }
}

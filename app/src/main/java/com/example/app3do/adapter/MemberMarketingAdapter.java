package com.example.app3do.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.models.personal.DataPersonal;

import java.util.List;

public class MemberMarketingAdapter extends RecyclerView.Adapter<MemberMarketingAdapter.MyHolder> {
    private boolean isGirdView;
    private int count;
    private List<DataPersonal> list;
    private int width;

    public void updateData(List<DataPersonal> list){
        this.list = list;
    }
    public void updateLayoutManager(boolean isGird){
        this.isGirdView = isGird;
    }

    public MemberMarketingAdapter(int width, int count, boolean isGirdView, List<DataPersonal> list) {
        this.isGirdView = isGirdView;
        this.list = list;
        this.width = width;
        this.count = count;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (isGirdView) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_member_f1_grid, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_member_f1_linear, parent, false);
        }

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DataPersonal dataPersonal = list.get(position);

        if (dataPersonal != null) {
            Glide.with(holder.itemView).load(dataPersonal.getAvatar()).into(holder.img_avatar);
            holder.txt_address.setText(dataPersonal.getAddress());
            holder.txt_name.setText(dataPersonal.getName());
            holder.txt_phone.setText(dataPersonal.getPhone());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView img_avatar;
        View view_avatar;
        TextView txt_name, txt_phone, txt_address;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            view_avatar = itemView.findViewById(R.id.view_avatar);
            img_avatar = itemView.findViewById(R.id.img_avatar);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_phone = itemView.findViewById(R.id.txt_phone);
            txt_address = itemView.findViewById(R.id.txt_address);

            int widthItem = width / count;

            if (isGirdView) {
                view_avatar.getLayoutParams().width = (int) Math.round(widthItem * 0.15700483091);
                view_avatar.getLayoutParams().height = (int) Math.round(widthItem * 0.15700483091);
                img_avatar.getLayoutParams().width = (int) Math.round(widthItem * 0.15700483091);
                img_avatar.getLayoutParams().height = (int) Math.round(widthItem * 0.15700483091);
            } else {
                view_avatar.getLayoutParams().width = (int) Math.round(width * 0.16666666666);
                view_avatar.getLayoutParams().height = (int) Math.round(width * 0.16666666666);
                img_avatar.getLayoutParams().width = (int) Math.round(width * 0.16666666666);
                img_avatar.getLayoutParams().height = (int) Math.round(width * 0.16666666666);
            }
        }
    }
}
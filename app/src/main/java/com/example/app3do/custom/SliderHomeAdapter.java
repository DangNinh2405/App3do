package com.example.app3do.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.models.home.DataSlide;

import java.util.List;

public class SliderHomeAdapter extends RecyclerView.Adapter<SliderHomeAdapter.MyHolder> {

    private List<DataSlide> list;
    private int width;

    public SliderHomeAdapter(List<DataSlide> list, int width) {
        this.list = list;
        this.width = width;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_slider_home, parent, false);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Glide.with(holder.itemView).load(list.get(position).getLinkImg()).into(holder.img_slider);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView img_slider;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img_slider = itemView.findViewById(R.id.img_slider);
            img_slider.getLayoutParams().width = (int) Math.round(width * 0.92270531401);
            img_slider.getLayoutParams().height = (int) Math.round(width * 0.35024154589);
        }
    }
}

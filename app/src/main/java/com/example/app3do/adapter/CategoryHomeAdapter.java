package com.example.app3do.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.models.home.DataCategory;

import java.util.List;

public class CategoryHomeAdapter extends  RecyclerView.Adapter<CategoryHomeAdapter.MyHolder>{

    private int width;
    private List<DataCategory> list;
    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public CategoryHomeAdapter(int width, List<DataCategory> list) {
        this.width = width;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_category_home, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DataCategory category = list.get(position);

        Glide.with(holder.itemView).load(category.getIcon()).into(holder.img_category);
        holder.txt_category_name.setText(category.getName());

        holder.lnl_item.setOnClickListener( v -> {
            this.itemOnClickListener.onClick(category);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView img_category;
        TextView txt_category_name;
        LinearLayout lnl_item;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img_category = itemView.findViewById(R.id.img_category);
            txt_category_name = itemView.findViewById(R.id.txt_category_name);
            lnl_item = itemView.findViewById(R.id.lnl_item);

            lnl_item.getLayoutParams().width = (int) Math.round(width / 4);
            img_category.getLayoutParams().width = (int) Math.round(width * 0.11594202898);
            img_category.getLayoutParams().height = (int) Math.round(width * 0.11594202898);

        }
    }

    public interface ItemOnClickListener {
        void onClick(DataCategory category);
    }
}

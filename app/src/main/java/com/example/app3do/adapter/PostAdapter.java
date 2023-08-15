package com.example.app3do.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.models.post.DataPost;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHolder> {
    private int widthScreen;
    private List<DataPost> list;

    private ItemOnClickListener itemOnClickListener;


    public PostAdapter(int widthScreen, List<DataPost> list) {
        this.widthScreen = widthScreen;
        this.list = list;
    }

    public void updateData(List<DataPost> list) {
        this.list = list;
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener){
        this.itemOnClickListener = itemOnClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_post, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DataPost post = list.get(position);

        if (post.getImage().length() > 0) {
            Glide.with(holder.itemView).load(post.getImage()).into(holder.img_post);
        }

        holder.txt_title.setText(post.getTitle());
        String time = post.getCreatedAt().getDate().split(" ")[0];

        holder.txt_time.setText(time);

        holder.rltl_item.setOnClickListener( v -> {
            itemOnClickListener.onClick(post, position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        RelativeLayout rltl_item;
        ImageView img_post;
        TextView txt_title, txt_time;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            rltl_item = itemView.findViewById(R.id.rltl_item);
            img_post = itemView.findViewById(R.id.img_post);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_time = itemView.findViewById(R.id.txt_time);

            int widthVsHeightImg = (int) Math.round(widthScreen * 0.20531400966);

            img_post.getLayoutParams().width = widthVsHeightImg;
            img_post.getLayoutParams().height = widthVsHeightImg;
        }
    }

    public interface ItemOnClickListener {
        void onClick(DataPost post, int position);
    }
}

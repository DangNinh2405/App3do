package com.example.app3do.custom;

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
import com.example.app3do.models.product.DataProduct;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductHomeAdapter extends RecyclerView.Adapter<ProductHomeAdapter.MyHolder> {
    private Locale locale = new Locale("vi", "VN");
    private NumberFormat format = NumberFormat.getCurrencyInstance(locale);
    private int width;
    private List<DataProduct> list;

    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public ProductHomeAdapter(int width, List<DataProduct> list) {
        this.width = width;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_product_in_rcv_home, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DataProduct product = list.get(position);

        Glide.with(holder.itemView).load(product.getAvatar().getUrl()).into(holder.img_product);
        holder.txt_product_name.setText(product.getName());
        holder.txt_product_price.setText(format.format(product.getPrice()));
        holder.txt_product_discount_percent.setText(String.valueOf(product.getMinPricePolicy().getDiscountPercentRound()));
        holder.lnl_item.setOnClickListener( v -> {
            itemOnClickListener.onClick(product);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout lnl_item;
        ImageView img_product;
        TextView txt_product_name, txt_product_price, txt_product_discount_percent;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            lnl_item = itemView.findViewById(R.id.lnl_item);
            img_product = itemView.findViewById(R.id.img_product);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
            txt_product_discount_percent = itemView.findViewById(R.id.txt_product_discount_percent);
            lnl_item.getLayoutParams().width = (int) Math.round(width * 0.32608695652);
        }
    }

    public interface ItemOnClickListener {
        void onClick(DataProduct product);
    }
}

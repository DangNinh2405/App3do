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
import com.example.app3do.models.product.DataProduct;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SeeAllProductHomeAdapter extends RecyclerView.Adapter<SeeAllProductHomeAdapter.MyHolder> {
    private Locale locale = new Locale("vi", "VN");
    private NumberFormat format = NumberFormat.getCurrencyInstance(locale);
    private List<DataProduct> list;
    private int width;
    private int countProductHorizontal;

    private ItemOnClickListener itemOnClickListener;
    public void updateData(List<DataProduct> list) {
        this.list = list;
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public SeeAllProductHomeAdapter(int width, int countProductHorizontal, List<DataProduct> list) {
        this.list = list;
        this.countProductHorizontal = countProductHorizontal;
        this.width = width;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_see_all_product_home, parent, false);
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
        holder.img_add_cart.setOnClickListener( v -> {
            itemOnClickListener.onClickAddToCart(product);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView img_product, img_add_cart;
        TextView txt_product_name, txt_product_price, txt_product_discount_percent;
        LinearLayout lnl_item;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            lnl_item = itemView.findViewById(R.id.lnl_item);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
            txt_product_discount_percent = itemView.findViewById(R.id.txt_product_discount_percent);
            img_product = itemView.findViewById(R.id.img_product);
            img_add_cart = itemView.findViewById(R.id.img_add_cart);

            img_product.getLayoutParams().height = (int) Math.round(((width - 20 * countProductHorizontal) / 2)  * 0.73770491803);
        }
    }

    public interface ItemOnClickListener {
        void onClick(DataProduct product);
        void onClickAddToCart(DataProduct product);
    }
}

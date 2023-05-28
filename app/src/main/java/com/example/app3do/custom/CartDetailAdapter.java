package com.example.app3do.custom;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.models.cart.DataCart;
import com.example.app3do.models.product.DataProduct;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartDetailAdapter extends RecyclerView.Adapter<CartDetailAdapter.MyHolder> {
    private Locale locale = new Locale("vi", "VN");
    private NumberFormat format = NumberFormat.getCurrencyInstance(locale);

    private int width;
    private List<DataCart> list;
    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public CartDetailAdapter(int width, List<DataCart> list) {
        this.width = width;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_cart_detail, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DataCart dataCart = list.get(position);

        if (dataCart != null) {
            Glide.with(holder.itemView).load(dataCart.getProduct().getAvatar().getUrl()).into(holder.img_product);
            holder.txt_product_name.setText(dataCart.getProduct().getName());
            holder.txt_discount_percent.setText(String.valueOf(dataCart.getProduct().getMinPricePolicy().getDiscountPercentRound()));

            holder.txt_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txt_price.setText(format.format(dataCart.getProduct().getPrice()));

            holder.txt_real_price.setText(format.format(dataCart.getProduct().getMinPricePolicy().getRealPrice()));
            holder.etxt_quantity.setText(String.valueOf(dataCart.getQuantity()));

            holder.txt_total_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txt_total_price.setText(format.format(dataCart.getProduct().getPrice() * dataCart.getQuantity()));

            holder.txt_total_real_price.setText(format.format(dataCart.getProduct().getMinPricePolicy().getRealPrice() * dataCart.getQuantity()));
            event(holder, dataCart);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView img_product, img_delete_product;
        EditText etxt_quantity;
        TextView txt_discount_percent, txt_price, txt_real_price, txt_total_price, txt_total_real_price, txt_product_name;
        Button btn_minus_quantity, btn_plus_quantity;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product);
            etxt_quantity = itemView.findViewById(R.id.etxt_quantity);

            txt_discount_percent = itemView.findViewById(R.id.txt_discount_percent);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_real_price = itemView.findViewById(R.id.txt_real_price);
            txt_total_price = itemView.findViewById(R.id.txt_total_price);
            txt_total_real_price = itemView.findViewById(R.id.txt_total_real_price);
            btn_minus_quantity = itemView.findViewById(R.id.btn_minus_quantity);
            btn_plus_quantity = itemView.findViewById(R.id.btn_plus_quantity);
            img_delete_product = itemView.findViewById(R.id.img_delete_product);

            etxt_quantity.getLayoutParams().width = (int) Math.round(width * 0.07729468599);
            img_product.getLayoutParams().width = (int) Math.round(width * 0.17);
            img_product.getLayoutParams().height = (int) Math.round(width * 0.17);
        }
    }

    private void event(MyHolder holder, DataCart dataCart) {
        holder.btn_minus_quantity.setOnClickListener( v -> {
            int count = -1;
            itemOnClickListener.minusProduct(dataCart.getProduct().getId(), count);
        });

        holder.btn_plus_quantity.setOnClickListener( v -> {
            int count = 1;
            itemOnClickListener.plusProduct(dataCart.getProduct().getId(), count);
        });

        holder.img_delete_product.setOnClickListener( v -> {
            int count = 0;
            itemOnClickListener.deleteProduct(dataCart.getProduct().getId(), count);
        });
    }

    public interface ItemOnClickListener {
        void minusProduct(int productId, int quantity);
        void plusProduct(int productId, int quantity);
        void deleteProduct(int productId, int quantity);
    }
}

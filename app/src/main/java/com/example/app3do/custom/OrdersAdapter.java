package com.example.app3do.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.models.order.DataOrder;
import com.example.app3do.models.personal.AddressesPersonal;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyHolder> {
    private Locale locale = new Locale("vi", "VN");
    private NumberFormat format = NumberFormat.getCurrencyInstance(locale);
    private int width;
    private ItemOnClickListener itemOnClickListener;
    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    private List<DataOrder> list;

    public void updateData(List<DataOrder> list){
        this.list = list;
    }

    public OrdersAdapter(int width, List<DataOrder> list) {
        this.width = width;
        this.list = list;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_personal_orders, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DataOrder order = list.get(position);

        if (order != null) {
            Glide.with(holder.itemView).load(order.getProducts().get(0).getAvatar().getUrl()).into(holder.img_name);

            holder.txt_code.setText("Đơn " + order.getCode());
            holder.txt_total_money.setText(format.format(order.getTotal_money()));
            holder.txt_status.setText(order.getStatus());
            holder.txt_date.setText(order.getCreatedAt().getDate());
            holder.txt_name.setText(order.getProducts().get(0).getName());
            holder.txt_total_point.setText(String.valueOf(order.getTotal_point()));

            holder.txt_cancel_order.setOnClickListener( v -> {
                itemOnClickListener.onClickCancel(order);
            });

            holder.txt_cancel_create_order.setOnClickListener( v -> {
                itemOnClickListener.onClickOrderAgain(order);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView img_name;
        TextView txt_code, txt_date, txt_name, txt_total_money, txt_total_point, txt_status, txt_cancel_create_order, txt_cancel_order;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img_name = itemView.findViewById(R.id.img_name);
            txt_code = itemView.findViewById(R.id.txt_code);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_total_money = itemView.findViewById(R.id.txt_total_money);
            txt_total_point = itemView.findViewById(R.id.txt_total_point);
            txt_cancel_create_order = itemView.findViewById(R.id.txt_cancel_create_order);
            txt_cancel_order = itemView.findViewById(R.id.txt_cancel_order);

            img_name.getLayoutParams().width = (int) Math.round(width * 0.13285024154);
            img_name.getLayoutParams().height = (int) Math.round(width * 0.13285024154);
        }
    }

    public interface ItemOnClickListener {
        void onClickCancel(DataOrder order);
        void onClickOrderAgain(DataOrder order);
    }
}

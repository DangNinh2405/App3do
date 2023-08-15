package com.example.app3do.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.models.personal.AddressesPersonal;

import java.util.List;

public class AddressCartAdapter extends RecyclerView.Adapter<AddressCartAdapter.MyHolder>  {
    private boolean check = false;
    private List<AddressesPersonal> list;
    private ItemOnClickListener itemOnClickListener;

    public void updateData(List<AddressesPersonal> list){
        this.list = list;
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public AddressCartAdapter(List<AddressesPersonal> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_address_cart, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        AddressesPersonal addressesPersonal = list.get(position);

        if (addressesPersonal != null) {
            holder.txt_name.setText(addressesPersonal.getName());
            holder.txt_phone_number.setText(addressesPersonal.getPhone());
            String address = addressesPersonal.getAddress() + ", " + addressesPersonal.getWard().getType() + " " + addressesPersonal.getWard().getName() + ", " + addressesPersonal.getDistrict().getType() + " " + addressesPersonal.getDistrict().getName() + ", " + addressesPersonal.getProvince().getType() + " " + addressesPersonal.getProvince().getName();
            holder.txt_address.setText(address);

            if (!check && addressesPersonal.getIsMain() == 1) {
                holder.rb_is_main.setChecked(true);
                check = true;
            }

            holder.img_clear.setOnClickListener( v -> {
                itemOnClickListener.onClear(addressesPersonal);
            });


            holder.rb_is_main.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        itemOnClickListener.onCheckedChanged(addressesPersonal, holder.getAdapterPosition());
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_phone_number, txt_address;
        ImageView img_clear;
        RadioButton rb_is_main;
        LinearLayout lnl_item;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            rb_is_main = itemView.findViewById(R.id.rb_is_main);
            lnl_item = itemView.findViewById(R.id.lnl_item);

            txt_address = itemView.findViewById(R.id.txt_address);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_phone_number = itemView.findViewById(R.id.txt_phone_number);
            img_clear = itemView.findViewById(R.id.img_clear);
        }

        public RadioButton getRbIsMain() {
            return rb_is_main;
        }
    }

    public List<AddressesPersonal> getList() {
        return list;
    }

    public interface ItemOnClickListener {
        void onClear(AddressesPersonal address);

        void onCheckedChanged(AddressesPersonal address, int position);
    }
}

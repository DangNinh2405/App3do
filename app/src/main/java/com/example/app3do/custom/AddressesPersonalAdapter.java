package com.example.app3do.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.models.personal.AddressesPersonal;

import java.util.List;

public class AddressesPersonalAdapter extends RecyclerView.Adapter<AddressesPersonalAdapter.MyHolder> {

    private List<AddressesPersonal> list;
    private ItemOnClickListener itemOnClickListener;
    public void updateData(List<AddressesPersonal> list){
        this.list = list;
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public AddressesPersonalAdapter(List<AddressesPersonal> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_address_personal_details, parent, false);
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

            holder.img_clear.setOnClickListener( v -> {
                itemOnClickListener.onClear(addressesPersonal);
            });

            holder.img_edit.setOnClickListener( v -> {
                itemOnClickListener.onEdit(addressesPersonal);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_phone_number, txt_address;
        ImageView img_edit, img_clear;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            txt_address = itemView.findViewById(R.id.txt_address);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_phone_number = itemView.findViewById(R.id.txt_phone_number);
            img_edit = itemView.findViewById(R.id.img_edit);
            img_clear = itemView.findViewById(R.id.img_clear);
        }
    }

    public interface ItemOnClickListener {
        void onEdit(AddressesPersonal address);
        void onClear(AddressesPersonal address);
    }
}

package com.example.app3do.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.models.personal.WardDistrictProvincePersonal;

import java.util.List;

public class AddressDialogAdapter extends RecyclerView.Adapter<AddressDialogAdapter.MyHolder> {

    private List<WardDistrictProvincePersonal> list;

    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public AddressDialogAdapter(List<WardDistrictProvincePersonal> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_address_dialog_personal, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        WardDistrictProvincePersonal addresses = list.get(position);
        if (addresses != null) {
            holder.txt_address.setText(addresses.getName());
            holder.txt_address.setOnClickListener( v -> {
                itemOnClickListener.onClick(addresses);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_address;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            txt_address = itemView.findViewById(R.id.txt_address);
        }
    }

    public interface ItemOnClickListener {
        void onClick(WardDistrictProvincePersonal address);
    }
}

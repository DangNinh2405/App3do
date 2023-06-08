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
import com.example.app3do.models.personal.BanksPersonal;

import java.util.List;

public class BanksPersonalAdapter extends RecyclerView.Adapter<BanksPersonalAdapter.MyHolder> {
    private List<BanksPersonal> list;

    private ItemOnClickListener itemOnClickListener;
    public void updateData(List<BanksPersonal> list){
        this.list = list;
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public BanksPersonalAdapter(List<BanksPersonal> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_bank_personal_details, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        BanksPersonal banks = list.get(position);

        if (banks != null) {
            holder.txt_account_name.setText(banks.getAccountName());
            holder.txt_banks_name.setText(banks.getName());
            holder.txt_account_number.setText(banks.getAccountNumber());
            holder.txt_branch.setText(banks.getBranch());

            holder.img_clear.setOnClickListener( v -> {
                itemOnClickListener.onClear(banks);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_banks_name, txt_account_number, txt_branch, txt_account_name;
        ImageView img_clear;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            txt_banks_name = itemView.findViewById(R.id.txt_banks_name);
            txt_account_number = itemView.findViewById(R.id.txt_account_number);
            txt_branch = itemView.findViewById(R.id.txt_branch);
            txt_account_name = itemView.findViewById(R.id.txt_account_name);
            img_clear = itemView.findViewById(R.id.img_clear);
        }
    }

    public interface ItemOnClickListener {
        void onClear(BanksPersonal bank);
    }
}

package com.example.app3do.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.models.personal.BanksPersonal;

import java.util.List;

public class BanksAccountAdapter extends RecyclerView.Adapter<BanksAccountAdapter.MyHolder> {
    private List<BanksPersonal> list;

    public BanksAccountAdapter(List<BanksPersonal> list) {
        this.list = list;
    }

    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_bank_account, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        BanksPersonal banks = list.get(position);

        holder.txt_account_name.setText(banks.getAccountName());
        holder.txt_banks_name.setText(banks.getName());
        holder.txt_account_number.setText(banks.getAccountNumber());
        holder.txt_branch.setText(banks.getBranch());

        holder.img_copy.setOnClickListener(v -> {
            if (itemOnClickListener != null) {
                itemOnClickListener.onClickCopy(banks);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_banks_name, txt_account_number, txt_branch, txt_account_name;
        ImageView img_copy;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            txt_banks_name = itemView.findViewById(R.id.txt_banks_name);
            txt_account_number = itemView.findViewById(R.id.txt_account_number);
            txt_branch = itemView.findViewById(R.id.txt_branch);
            txt_account_name = itemView.findViewById(R.id.txt_account_name);
            img_copy = itemView.findViewById(R.id.img_copy);
        }
    }

    public interface ItemOnClickListener {
        void onClickCopy(BanksPersonal bank);
    }
}

package com.example.quanlychitieu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<DataClass> dataList;

    public RecycleViewAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_calendar, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataClass item = dataList.get(position);
        holder.recycleCategory.setText(item.getCategories().getName());
        holder.recycleNote.setText(item.getTransaction().getNote());
        int amt = item.getTransaction().getAmount();
        if (item.getCategories().getType() == Categories.TransactionType.THU) {
            holder.recyclePrice.setText(String.format("+%d đ", amt));
            holder.recyclePrice.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            holder.recyclePrice.setText(String.format("−%d đ", amt));
            holder.recyclePrice.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView recycleCategory, recycleNote, recyclePrice;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recycleCategory = itemView.findViewById(R.id.recycleCategory);
        recycleNote = itemView.findViewById(R.id.recycleNote);
        recyclePrice = itemView.findViewById(R.id.recyclePrice);
    }
}
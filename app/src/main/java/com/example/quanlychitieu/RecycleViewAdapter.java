package com.example.quanlychitieu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recycleCategory.setText(dataList.get(position).getCategories().getName());
        int amt = dataList.get(position).getTransaction().getAmount();
        holder.recyclePrice.setText(String.valueOf(amt));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView recycleCategory, recyclePrice;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recycleCategory = itemView.findViewById(R.id.recycleCategory);
        recyclePrice = itemView.findViewById(R.id.recyclePrice);
    }
}
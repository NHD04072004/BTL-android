package com.example.quanlychitieu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BudgetRecycleViewAdapter extends RecyclerView.Adapter<BudgetViewHoder> {
    private Context context;
    private List<Budget> dataList;

    public BudgetRecycleViewAdapter(Context context, List<Budget> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public BudgetViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_budget, parent, false);
        return new BudgetViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHoder holder, int position) {
        Budget item = dataList.get(position);
        holder.tvCategoryTitle.setText(item.getCategory().getName());
        holder.spentMoney.setText(String.valueOf(item.getSpent()));
        holder.sumMoney.setText(String.valueOf(item.getBudgetAmount()));
        int remaining = item.getBudgetAmount() - item.getSpent();
        holder.conlai.setText(String.valueOf(remaining));

        if (remaining < 0) {
            holder.conlai.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        } else {
            holder.conlai.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        }

        holder.btnMore.setOnClickListener(v -> {
            // xử lý popup menu 3 chấm
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}

class BudgetViewHoder extends RecyclerView.ViewHolder {
    TextView tvCategoryTitle, conlai, spentMoney, sumMoney;
    ImageButton btnMore;

    public BudgetViewHoder(@NonNull View itemView) {
        super(itemView);

        tvCategoryTitle = itemView.findViewById(R.id.tvCategoryTitle);
        conlai = itemView.findViewById(R.id.conlai);
        spentMoney = itemView.findViewById(R.id.spentMoney);
        sumMoney = itemView.findViewById(R.id.sumMoney);
        btnMore = itemView.findViewById(R.id.btnMore);
    }
}
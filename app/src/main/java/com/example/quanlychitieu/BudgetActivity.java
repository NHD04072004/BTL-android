package com.example.quanlychitieu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BudgetActivity extends AppCompatActivity implements Refreshable {
    private TextView budget_idDate, budget_overtime, displayBudget, displayTienChi;
    private Button addBudget;
    private DatabaseHelper db;
    private BudgetRecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.budget);

        budget_idDate = findViewById(R.id.budget_idDate);
        budget_overtime = findViewById(R.id.budget_overtime);
        displayTienChi = findViewById(R.id.displayTienChi);
        displayBudget = findViewById(R.id.displayBudget);

        addBudget = findViewById(R.id.addBudget);
        addBudget.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddBudgetActivity.class);
            startActivity(intent);
        });


        String today = DateUtils.getFullDateVietnamese(new Date());
        budget_idDate.setText(today);
        Calendar today_ = Calendar.getInstance();
        int lastDay = today_.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDay = today_.get(Calendar.DAY_OF_MONTH);
        int daysLeft = lastDay - currentDay;
        budget_overtime.setText(String.valueOf(daysLeft));

        db = new DatabaseHelper(this);

        pieChart = findViewById(R.id.half_chart);

        int totalChi = db.getTotalByType(Categories.TransactionType.CHI);
        int totalBudget = db.getUserBudget();
        int remain = totalBudget - totalChi;
        displayTienChi.setText(String.valueOf(totalChi));
        displayBudget.setText(String.valueOf(totalBudget));

        List<Budget> budgets = new ArrayList<>();
        List<Categories> categories = db.getAllCategoriesWithChi();
        for (Categories cat : categories) {
            int bud = db.getBudgetAmountByCategory(cat.getId());
            int spent = db.getTotalByCategory(cat.getId());
            budgets.add(new Budget(cat, bud, spent));
        }
        recyclerView = findViewById(R.id.recyclerViewBudget);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new BudgetRecycleViewAdapter(this, budgets);
        recyclerView.setAdapter(adapter);

        List<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(totalChi, "Đã chi"));
        entries.add(new PieEntry(remain, "Còn lại"));

        PieDataSet pieDataSet = new PieDataSet(entries, "Label");
        pieDataSet.setColors(Color.RED, Color.GREEN);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(12f);

        pieChart.setUsePercentValues(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleRadius(0f);
        pieChart.setMaxAngle(180f);
        pieChart.setRotationAngle(180f);
        pieChart.setDrawCenterText(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDrawEntryLabels(false);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.input) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            } else if (id == R.id.calendar) {
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                finish();
                return true;
            } else if (id == R.id.budget) {
                return true;
            } else if (id == R.id.report) {
                startActivity(new Intent(getApplicationContext(), ReportActivity.class));
                finish();
                return true;
            } else return false;
        });
    }

    @Override
    public void refreshData() {

    }
}
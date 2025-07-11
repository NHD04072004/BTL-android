package com.example.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<DataClass> dataList;
    private CalendarRecycleViewAdapter adapter;
    private DatabaseHelper db;
    private CalendarView calendarView;
    private TextView idDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar);
        db = new DatabaseHelper(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.calendar);
        calendarView = findViewById(R.id.calendarView2);

        recyclerView = findViewById(R.id.recyclerViewCalendar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        idDate = findViewById(R.id.idDate);

        String today = DateUtils.getCurrentDateString();
        idDate.setText(today);
        loadTransactionsForDate(today);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
            idDate.setText(selectedDate);
            loadTransactionsForDate(selectedDate);
        });

        adapter = new CalendarRecycleViewAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.input) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            } else if (id == R.id.calendar) {
                return true;
            } else if (id == R.id.budget) {
                startActivity(new Intent(getApplicationContext(), BudgetActivity.class));
                finish();
                return true;
            } else if (id == R.id.report) {
                startActivity(new Intent(getApplicationContext(), ReportActivity.class));
                finish();
                return true;
            } else return false;
        });
    }

    private void loadTransactionsForDate(String date) {
        dataList = db.getTransactionsByDate(date);
        adapter = new CalendarRecycleViewAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
    }

}
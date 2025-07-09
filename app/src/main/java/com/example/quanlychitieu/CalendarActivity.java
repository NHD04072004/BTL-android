package com.example.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DataClass> dataList;
    RecycleViewAdapter adapter;
    DataClass androidData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.calendar);

        recyclerView = findViewById(R.id.recyclerView);
        Log.d("DEBUG", "RV = " + recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();
        Categories cat1 = new Categories(1, "Ăn uống", Categories.TransactionType.CHI);
        Transaction tx1 = new Transaction(1, 70000, cat1.getId(), new Date(), "Cà phê sáng");
        Categories cat2 = new Categories(2, "Luong", Categories.TransactionType.THU);
        Transaction tx2 = new Transaction(2, 100000, cat2.getId(), new Date(), "Luong");

        androidData = new DataClass(cat1, tx1);
        dataList.add(androidData);
        androidData = new DataClass(cat2, tx2);
        dataList.add(androidData);

        adapter = new RecycleViewAdapter(this, dataList);
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
            } else if (id == R.id.setting) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                finish();
                return true;
            } else return false;
        });
    }
}
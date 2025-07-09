package com.example.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.setting);

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
                startActivity(new Intent(getApplicationContext(), BudgetActivity.class));
                finish();
                return true;
            } else if (id == R.id.report) {
                startActivity(new Intent(getApplicationContext(), ReportActivity.class));
                finish();
                return true;
            } else if (id == R.id.setting) {
                return true;
            } else return false;
        });
    }
}
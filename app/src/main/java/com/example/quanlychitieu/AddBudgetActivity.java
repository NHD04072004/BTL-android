package com.example.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class AddBudgetActivity extends AppCompatActivity {
    private ImageButton imageBack;
    private Spinner spinner;
    private EditText amountInput;
    private Button saveButton;
    private DatabaseHelper db;
    private List<Categories> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_budget);

        imageBack = findViewById(R.id.imageBack);
        spinner = findViewById(R.id.spinner);
        amountInput = findViewById(R.id.amountInput);
        saveButton = findViewById(R.id.save);

        db = new DatabaseHelper(this);

        List<Categories> categories = db.getAllCategoriesWithChi();
        List<String> categoryNames = new ArrayList<>();
        for (Categories c : categories) {
            categoryNames.add(c.getName());
        }
        Log.d("categoryNames", "onCreate: " + categoryNames);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        imageBack.setOnClickListener(v -> finish());
        loadCategoriesIntoSpinner();
        saveButton.setOnClickListener(v -> {
            int pos = spinner.getSelectedItemPosition();
            String amountStr = amountInput.getText().toString().trim();

            if (amountStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số tiền ngân sách", Toast.LENGTH_SHORT).show();
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Số tiền không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            Categories selected = categoryList.get(pos);
            boolean ok = db.insertBudget(selected.getId(), amount);
            if (ok) {
                Toast.makeText(this, "Đã thêm ngân sách thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, BudgetActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Lỗi thêm ngân sách", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadCategoriesIntoSpinner() {
        categoryList = db.getAllCategoriesWithChi();
        List<String> labels = new ArrayList<>();
        for (Categories c : categoryList) {
            labels.add(c.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, labels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
package com.example.quanlychitieu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddCategoryActivity extends AppCompatActivity {
    private TextView textView;
    private Spinner spinner;
    private Button button;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_category);
        textView = findViewById(R.id.edittextName);
        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.save);
        db = new DatabaseHelper(this);

        String[] transactionTypes = new String[Categories.TransactionType.values().length];
        for (int i = 0; i < Categories.TransactionType.values().length; i++) {
            transactionTypes[i] = Categories.TransactionType.values()[i].name();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, transactionTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        button.setOnClickListener(v -> saveCategory());
    }

    private void saveCategory() {
        String name = textView.getText().toString().trim();
        String transactionType = spinner.getSelectedItem().toString().toLowerCase();

        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên danh mục!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean inserted = db.insertCategory(name, transactionType);
        Log.d("INSERT CATEGORY", "saveCategory: " + inserted);

        if (inserted) {
            Toast.makeText(this, "Thêm danh mục thành công!", Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
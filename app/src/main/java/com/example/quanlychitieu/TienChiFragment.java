package com.example.quanlychitieu;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TienChiFragment extends Fragment implements Refreshable {
    private GridLayout gridCategories;
    private DatabaseHelper db;
    private EditText etDate, etNote, etAmount;
    private Button btnSaveTransaction;
    private Button selectedButton = null;
    private Categories selectedCategory = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tien_chi, container, false);
        db = new DatabaseHelper(requireContext());
        gridCategories = view.findViewById(R.id.grid_layout);
        etDate = view.findViewById(R.id.edittextDate);
        etNote = view.findViewById(R.id.edittextNote);
        etAmount = view.findViewById(R.id.edittextTienChi);
        btnSaveTransaction = view.findViewById(R.id.btnNhapTienChi);

        etDate.setFocusable(false);
        etDate.setKeyListener(null);
        etDate.setOnClickListener(v -> {
            DatePickerFragment dp = new DatePickerFragment();
            Bundle b = new Bundle();
            b.putInt("targetId", R.id.edittextDate);
            dp.setArguments(b);
            dp.show(getParentFragmentManager(), "datePicker");
        });

        displayCategories();
        btnSaveTransaction.setOnClickListener(v -> saveTransaction());
        return view;
    }

    private void displayCategories() {
        List<Categories> categories = db.getAllCategoriesWithChi();

        gridCategories.removeAllViews();

        for (Categories category : categories) {
            View item = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, gridCategories, false);

            Button btn = item.findViewById(R.id.gridButton);
            btn.setText(category.getName());
            btn.setOnClickListener(v -> {
                if (selectedButton != null && selectedButton != btn) {
                    selectedButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
                }
                btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
                selectedButton = btn;

                selectedCategory = category;
            });

            gridCategories.addView(item);
        }
    }

    private void saveTransaction() {
        if (selectedCategory == null) {
            Toast.makeText(getContext(), "Chọn danh mục!", Toast.LENGTH_SHORT).show();
            return;
        }

        String sDate = etDate.getText().toString().trim();
        String note = etNote.getText().toString().trim();
        String sAmt = etAmount.getText().toString().trim();

        if (sDate.isEmpty() || sAmt.isEmpty()) {
            Toast.makeText(getContext(), "Nhập đầy đủ ngày và số tiền!", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(sAmt);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Số tiền không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        Date date;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(sDate);
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Ngày không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean ok = db.insertTransaction(amount, selectedCategory.getId(), sDate, note);

        if (ok) {
            Toast.makeText(getContext(), "Lưu giao dịch thành công!", Toast.LENGTH_SHORT).show();
            etDate.setText("");
            etNote.setText("");
            etAmount.setText("");
            selectedButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            selectedButton = null;
            selectedCategory = null;
        } else {
            Toast.makeText(getContext(), "Lưu thất bại!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void refreshData() {
        displayCategories();
    }
}
package com.example.quanlychitieu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String getCurrentDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String getFullDateVietnamese(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("'Ngày' dd 'tháng' MM 'năm' yyyy", Locale.forLanguageTag("vi-VN"));
        return sdf.format(date);
    }


    public static Date convertStringToDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}

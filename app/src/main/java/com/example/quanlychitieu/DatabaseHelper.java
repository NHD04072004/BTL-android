package com.example.quanlychitieu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String databaseName = "Expenses.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Categories (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, transactionType TEXT NOT NULL CHECK(transactionType IN ('thu','chi')));");
        db.execSQL("CREATE TABLE Transactions (id INTEGER PRIMARY KEY AUTOINCREMENT, amount INTEGER NOT NULL, category_id INTEGER NOT NULL REFERENCES Categories(id) ON DELETE CASCADE, date TEXT NOT NULL, note TEXT);");
        db.execSQL("CREATE TABLE Budgets (category_id INTEGER NOT NULL REFERENCES Categories(id) ON DELETE CASCADE, amount INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertCategory(String name, String transactionType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("transactionType", transactionType);

        long res = db.insert("Categories", null, contentValues);
        return res != -1;
    }

    public boolean insertTransaction(int amount, int categoryId, String date, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("amount", amount);
        contentValues.put("category_id", categoryId);
        contentValues.put("date", date);
        contentValues.put("note", note);

        long res = db.insert("Transactions", null, contentValues);
        return res != -1;
    }

    public List<DataClass> getAllTransactions() {
        List<DataClass> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT t.id, t.amount, t.category_id, t.date, t.note, c.id, c.name, c.transactionType " +
                "FROM Transactions t " +
                "JOIN Categories c ON t.category_id = c.id " +
                "ORDER BY t.date DESC";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int tId = cursor.getInt(0);
                int amount = cursor.getInt(1);
                int catId = cursor.getInt(2);
                String date = cursor.getString(3);
                String note = cursor.getString(4);
                int cId = cursor.getInt(5);
                String name = cursor.getString(6);
                String type = cursor.getString(7);
                Categories.TransactionType tt = Categories.TransactionType.valueOf(type.toUpperCase());

                Categories cat = new Categories(cId, name, tt);
                Transaction tx = new Transaction(tId, amount, catId, new Date(), note);
                list.add(new DataClass(cat, tx));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Categories> getAllCategoriesWithThu() {
        List<Categories> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name, transactionType FROM Categories WHERE transactionType = 'thu'", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Categories(
                        cursor.getInt(0),
                        cursor.getString(1),
                        Categories.TransactionType.valueOf(cursor.getString(2).toUpperCase())
                ));
            } while (cursor.moveToNext());
        }
        Log.d("Categories", "getAllCategories: " + list);
        cursor.close();
        return list;
    }

    public List<Categories> getAllCategoriesWithChi() {
        List<Categories> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name, transactionType FROM Categories WHERE transactionType = 'chi'", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Categories(
                        cursor.getInt(0),
                        cursor.getString(1),
                        Categories.TransactionType.valueOf(cursor.getString(2).toUpperCase())
                ));
            } while (cursor.moveToNext());
        }
        Log.d("Categories", "getAllCategories: " + list);
        cursor.close();
        return list;
    }

    public List<DataClass> getTransactionsByDate(String date) {
        List<DataClass> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT t.id, t.amount, t.category_id, t.date, t.note, c.name, c.transactionType " +
                "FROM Transactions t JOIN Categories c ON t.category_id = c.id " +
                "WHERE t.date = ? ORDER BY t.date DESC";

        Cursor cursor = db.rawQuery(query, new String[]{date});

        if (cursor.moveToFirst()) {
            do {
                int transactionId = cursor.getInt(0);
                int amount = cursor.getInt(1);
                int categoryId = cursor.getInt(2);
                String dateStr = cursor.getString(3);
                String note = cursor.getString(4);
                String categoryName = cursor.getString(5);
                String transactionType = cursor.getString(6);
                Categories.TransactionType tt = Categories.TransactionType.valueOf(transactionType.toUpperCase());

                Categories category = new Categories(categoryId, categoryName, tt);
                Transaction transaction = new Transaction(transactionId, amount, categoryId, DateUtils.convertStringToDate(dateStr), note);
                DataClass dataClass = new DataClass(category, transaction);
                list.add(dataClass);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public int getTotalByType(Categories.TransactionType type) {
        SQLiteDatabase db = this.getReadableDatabase();
        String typeStr = type.name().toLowerCase();
        String sql = "SELECT SUM(amount) FROM Transactions t "
                + "JOIN Categories c ON t.category_id = c.id "
                + "WHERE c.transactionType = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{typeStr});
        int total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.isNull(0) ? 0 : cursor.getInt(0);
        }
        cursor.close();
        return total;
    }

    public int getUserBudget() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM Budgets", null);
        int budget = 0;
        if (cursor.moveToFirst()) {
            budget = cursor.getInt(0);
        }
        cursor.close();
        return budget;
    }

    public int getUserBudgetByCategoryId(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT amount FROM Budgets WHERE category_id = ?",
                new String[]{String.valueOf(categoryId)}
        );
        int budget = 0;
        if (cursor.moveToFirst()) {
            budget = cursor.getInt(0);
        }
        cursor.close();
        return budget;
    }

    public boolean insertBudget(int categoryId, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_id", categoryId);
        contentValues.put("amount", amount);
        long res = db.insert("Budgets", null, contentValues);
        return res != -1;
    }

    public int getBudgetAmountByCategory(int catId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT amount FROM Budgets WHERE category_id = ?",
                new String[]{String.valueOf(catId)});
        if (c.moveToFirst()) {
            int v = c.getInt(0);
            c.close();
            return v;
        }
        c.close();
        return 0;
    }

    public int getTotalByCategory(int catId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT SUM(amount) FROM Transactions WHERE category_id = ?",
                new String[]{String.valueOf(catId)});
        int sum = 0;
        if (c.moveToFirst()) {
            sum = c.isNull(0) ? 0 : c.getInt(0);
        }
        c.close();
        return sum;
    }


}

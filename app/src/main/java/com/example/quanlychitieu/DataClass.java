package com.example.quanlychitieu;

public class DataClass {
    private Categories categories;
    private Transaction transaction;

    public DataClass(Categories categories, Transaction transaction) {
        this.categories = categories;
        this.transaction = transaction;
    }

    public Categories getCategories() {
        return categories;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}

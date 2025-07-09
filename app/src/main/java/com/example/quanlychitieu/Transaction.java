package com.example.quanlychitieu;

import java.util.Date;

public class Transaction {
    private int id;
    private int amount;
    private int category_id;
    private Date date;
    private String note;

    public Transaction(){

    }
    public Transaction(int id, int amount, int category_id, Date date, String note) {
        this.id = id;
        this.amount = amount;
        this.category_id = category_id;
        this.date = date;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

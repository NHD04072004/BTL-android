package com.example.quanlychitieu;

public class Categories {
    private int id;
    private String name;
    private TransactionType type;

    public enum TransactionType {
        THU, CHI
    }

    public Categories(){

    }
    public Categories(int id, String name, TransactionType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}

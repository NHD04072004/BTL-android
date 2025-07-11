package com.example.quanlychitieu;

public class Budget {
    private Categories category;
    private int budgetAmount;
    private int spent; // tổng đã chi

    public Budget(Categories category, int budgetAmount, int spent) {
        this.category = category;
        this.budgetAmount = budgetAmount;
        this.spent = spent;
    }

    public Categories getCategory() { return category; }
    public int getBudgetAmount() { return budgetAmount; }
    public int getSpent() { return spent; }
}

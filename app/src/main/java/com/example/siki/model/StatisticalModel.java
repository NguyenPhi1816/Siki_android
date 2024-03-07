package com.example.siki.model;

public class StatisticalModel {
    String  title;
    int quantity;

    public StatisticalModel() {
    }

    public StatisticalModel(String title, int quantity) {
        this.title = title;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

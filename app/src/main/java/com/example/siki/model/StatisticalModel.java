package com.example.siki.model;

public class StatisticalModel {
    String  title;
    Double quantity;

    public StatisticalModel() {
    }

    public StatisticalModel(String title, Double quantity) {
        this.title = title;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}

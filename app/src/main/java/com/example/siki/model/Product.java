package com.example.siki.model;

import java.io.Serializable;

public class Product implements Serializable {
    private Long id;
    private String name;
    private String imagePath;
    private Double price;
    private int quantity;
    private Store store;

    public Product() {
    }

    public Product(Long id, String name, String imagePath, Double price, int quantity, Store store) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
        this.quantity = quantity;
        this.store = store;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", productPrice=" + price +
                ", quantity=" + quantity +
                ", store=" + store +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}

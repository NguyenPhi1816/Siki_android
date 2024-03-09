package com.example.siki.model;

import java.io.Serializable;

public class Product implements Serializable {
    private Long id;
    private String name;
    private String imagePath;
    private ProductPrice productPrice;
    private Store store;

    public Product() {
    }

    public Product(Long id, String name, String imagePath, ProductPrice productPrice, Store store) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.productPrice = productPrice;
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }
}

package com.example.siki.model;

public class Product {
    private Long id;
    private String name;
    private String imagePath;
    private ProductPrice productPrice;

    public Product() {
    }

    public Product(Long id, String name, String imagePath, ProductPrice productPrice) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.productPrice = productPrice;
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
}

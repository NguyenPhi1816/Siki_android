package com.example.siki.model;

public class Product {
    private Integer id;
    private String name;
    private Integer productPriceId;
    private String description;
    private Integer quantity;
    private Integer storeId;
    private String status;

    public Product(Integer id, String name, Integer productPriceId, String description, Integer quantity, Integer storeId, String status) {
        this.id = id;
        this.name = name;
        this.productPriceId = productPriceId;
        this.description = description;
        this.quantity = quantity;
        this.storeId = storeId;
        this.status = status;
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductPriceId() {
        return productPriceId;
    }

    public void setProductPriceId(Integer productPriceId) {
        this.productPriceId = productPriceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productPriceId=" + productPriceId +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", storeId=" + storeId +
                ", status='" + status + '\'' +
                '}';
    }
}

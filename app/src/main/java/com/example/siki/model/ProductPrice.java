package com.example.siki.model;

import java.time.LocalDateTime;

public class ProductPrice {
    private Long id;
    private Double price;

    private LocalDateTime updatedAt;

    public ProductPrice() {
    }

    public ProductPrice(Long id, Double price, LocalDateTime updatedAt) {
        this.id = id;
        this.price = price;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "id=" + id +
                ", price=" + price +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

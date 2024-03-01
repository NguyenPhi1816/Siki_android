package com.example.siki.model;

import java.util.Date;

public class ProductPrice {
    private Integer id;
    private double price;
    private Date updateAt;

    @Override
    public String toString() {
        return "ProductPrice{" +
                "id=" + id +
                ", price=" + price +
                ", updateAt=" + updateAt +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public ProductPrice() {
    }

    public ProductPrice(Integer id, double price, Date updateAt) {
        this.id = id;
        this.price = price;
        this.updateAt = updateAt;
    }
}

package com.example.siki.dto.product;

import com.example.siki.dto.StoreDto;
import com.example.siki.model.Store;

import java.util.List;

public class ProductVariantDto {

    private Long id;
    private String name;
    private String image;
    private int quantity;
    private Double price;
    private StoreDto store;

    public ProductVariantDto() {
    }

    public StoreDto getStore() {
        return store;
    }

    public void setStore(StoreDto store) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


}

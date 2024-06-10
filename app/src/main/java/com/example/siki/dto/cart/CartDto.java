package com.example.siki.dto.cart;

import com.example.siki.dto.product.ProductVariantDto;

public class CartDto {
    private Long id;
    private ProductVariantDto product;
    private int quantity;
    private boolean isSelected;

    public CartDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductVariantDto getProduct() {
        return product;
    }

    public void setProduct(ProductVariantDto product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

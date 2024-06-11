package com.example.siki.dto.order;

import com.example.siki.model.Cart;

public class OrderDetailPostDto {

    private Long productId;
    private int quantity;
    private double price;

    public OrderDetailPostDto() {
    }

    public OrderDetailPostDto(Cart cart) {
        this.productId = cart.getProduct().getId();
        this.quantity = cart.getQuantity();
        this.price = cart.getProduct().getPrice();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

package com.example.siki.model;

import com.example.siki.dto.cart.CartDto;

import java.io.Serializable;

public class Cart implements Serializable {
    private Long id;
    private Product product;
    private User user;
    private int quantity;

    private boolean isChosen;

    public Cart() {
    }

    public Cart(Long id, Product product, User user, int quantity, boolean isChosen) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.quantity = quantity;
        this.isChosen = isChosen;
    }

    public Cart(CartDto cartDto) {
        this.id = cartDto.getId();
        this.product = new Product(cartDto.getProduct());
        this.user = null;
        this.quantity = cartDto.getQuantity();
        this.isChosen = cartDto.isSelected();
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", product=" + product +
                ", user=" + user +
                ", quantity=" + quantity +
                '}';
    }
}

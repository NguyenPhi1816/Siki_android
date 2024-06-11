package com.example.siki.dto.order;

import com.example.siki.dto.product.ProductVariantDto;
import com.example.siki.enums.OrderStatus;

import java.util.List;

public class OrderDetailDto {
    private Long id;

    private ProductVariantDto productVariantDto;

    private Double price;

    private int quantity;

    public OrderDetailDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductVariantDto getProductVariantDto() {
        return productVariantDto;
    }

    public void setProductVariantDto(ProductVariantDto productVariantDto) {
        this.productVariantDto = productVariantDto;
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
}

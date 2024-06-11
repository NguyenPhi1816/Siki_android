package com.example.siki.model;

import com.example.siki.dto.order.OrderDetailDto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDetail implements Serializable {
    private Long id;

    private Order order;

    private Product product;

    private Double price;

    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(Long id, Order order, Product product, Double price, int quantity) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderDetail(OrderDetailDto orderDetailDto) {
        this.id = orderDetailDto.getId();
        this.product = new Product(orderDetailDto.getProductVariantDto());
        this.price = orderDetailDto.getPrice();
        this.quantity = orderDetailDto.getQuantity();
    }

    public List<OrderDetail> mapper(List<OrderDetailDto> orderDetailDtos) {
       return orderDetailDtos.stream().map(orderDetailDto -> new OrderDetail(orderDetailDto)).collect(Collectors.toList());
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

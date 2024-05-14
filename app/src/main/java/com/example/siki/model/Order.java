package com.example.siki.model;

import com.example.siki.enums.OrderStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private Long id;
    private String receiverPhoneNumber;
    private String receiverAddress;
    private String receiverName;
    private String note;
    private LocalDateTime createdAt;
    private OrderStatus status;

    private User user;

    private List<OrderDetail> orderDetails = new ArrayList<>();


    public Order() {
    }

    public Order(Long id, String receiverPhoneNumber, String receiverAddress, String receiverName, String note, LocalDateTime createdAt, OrderStatus status, User user, List<OrderDetail> orderDetails) {
        this.id = id;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.receiverAddress = receiverAddress;
        this.receiverName = receiverName;
        this.note = note;
        this.createdAt = createdAt;
        this.status = status;
        this.user = user;
        this.orderDetails = orderDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", receiverPhoneNumber='" + receiverPhoneNumber + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", note='" + note + '\'' +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", user=" + user +
                ", orderDetails=" + orderDetails +
                '}';
    }
}

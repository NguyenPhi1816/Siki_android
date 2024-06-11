package com.example.siki.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.siki.dto.order.OrderDto;
import com.example.siki.dto.order.OrderStatusDto;
import com.example.siki.enums.OrderStatus;
import com.example.siki.utils.DateFormatter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Order(OrderDto orderDto) {
        this.id = orderDto.getId();
        this.receiverPhoneNumber = orderDto.getReceiverPhoneNumber();
        this.receiverAddress = orderDto.getReceiverAddress();
        this.receiverName = orderDto.getReceiverName();
        this.note = orderDto.getNote();
        this.createdAt = DateFormatter.formatStringToLocalDateTime(orderDto.getCreatedAt());
        this.status = getByOrderDto(orderDto);
        this.orderDetails =   orderDto.getOrderDetails().stream().map(orderDetailDto -> new OrderDetail(orderDetailDto)).collect(Collectors.toList());;
    }

    public OrderStatus getByOrderDto(OrderDto orderDto) {
        if (orderDto.getStatus().equals(OrderStatusDto.PENDING.toString())) {
            return OrderStatus.Pending;
        }
        if (orderDto.getStatus().equals(OrderStatusDto.SHIPPING.toString())) {
            return OrderStatus.Shipping;
        }
        if (orderDto.getStatus().equals(OrderStatusDto.SUCCESS.toString())) {
            return OrderStatus.Success;
        }
        return OrderStatus.Pending;
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

package com.example.siki.dto.order;

import java.util.List;

public class OrderPostDto {

    private String receiverPhoneNumber;
    private String receiverAddress;
    private String receiverName;
    private String note;
    private List<OrderDetailPostDto> orderDetails;

    public OrderPostDto() {
    }

    public OrderPostDto(String receiverPhoneNumber, String receiverAddress, String receiverName, String note, List<OrderDetailPostDto> orderDetails) {
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.receiverAddress = receiverAddress;
        this.receiverName = receiverName;
        this.note = note;
        this.orderDetails = orderDetails;
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

    public List<OrderDetailPostDto> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailPostDto> orderDetails) {
        this.orderDetails = orderDetails;
    }
}

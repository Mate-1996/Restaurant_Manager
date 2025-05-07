package com.example.restaurant.entities;

import java.time.LocalDateTime;

public class Reservation {
    private int reservationId;
    private String customerName;
    private int tableNumber;
    private LocalDateTime reservationTime;
    private LocalDateTime createdAt;
    private String orderSummary;
    private double totalPrice; //

    public Reservation() {}

    public Reservation(int reservationId, String customerName, int tableNumber,
                       LocalDateTime reservationTime, LocalDateTime createdAt,
                       String orderSummary) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.tableNumber = tableNumber;
        this.reservationTime = reservationTime;
        this.createdAt = createdAt;
        this.orderSummary = orderSummary;
    }

    // Overloaded constructor including total price
    public Reservation(int reservationId, String customerName, int tableNumber,
                       LocalDateTime reservationTime, LocalDateTime createdAt,
                       String orderSummary, double totalPrice) {
        this(reservationId, customerName, tableNumber, reservationTime, createdAt, orderSummary);
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getOrderSummary() {
        return orderSummary;
    }

    public void setOrderSummary(String orderSummary) {
        this.orderSummary = orderSummary;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}



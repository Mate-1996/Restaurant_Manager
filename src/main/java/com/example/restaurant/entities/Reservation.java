package com.example.restaurant.entities;

import java.time.LocalDateTime;

public class Reservation {

    private int reservationId;
    private String customerName;
    private int tableNumber;
    private LocalDateTime reservationTime;

    public Reservation() {}

    public Reservation(int reservationId, String customerName, int tableNumber, LocalDateTime reservationTime) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.tableNumber = tableNumber;
        this.reservationTime = reservationTime;
    }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    public LocalDateTime getReservationTime() { return reservationTime; }
    public void setReservationTime(LocalDateTime reservationTime) { this.reservationTime = reservationTime; }
}

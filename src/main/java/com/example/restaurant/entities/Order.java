package com.example.restaurant.entities;

import java.util.List;

public class Order {

    private int orderId;
    private int tableNumber;
    private List<MenuItem> items; // List of ordered menu items
    private String status; // e.g., "Pending", "Completed"

    public Order() {}

    public Order(int orderId, int tableNumber, List<MenuItem> items, String status) {
        this.orderId = orderId;
        this.tableNumber = tableNumber;
        this.items = items;
        this.status = status;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    public List<MenuItem> getItems() { return items; }
    public void setItems(List<MenuItem> items) { this.items = items; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

package com.example.restaurant.entities;

import java.util.List;

public class Order {

    private int orderId;
    private int tableNumber;
    private List<MenuItem> items; // Used during order creation or full detail view
    private String status; // e.g., "Pending", "Completed"
    private List<String> itemNames;
    private double totalPrice;

    // Default constructor
    public Order() {}

    // Constructor for full order
    public Order(int orderId, int tableNumber, List<MenuItem> items, String status) {
        this.orderId = orderId;
        this.tableNumber = tableNumber;
        this.items = items;
        this.status = status;
    }

    // Constructor for admin summary (used in getAllOrdersWithItems())
    public Order(int orderId, int tableNumber, List<String> itemNames, double totalPrice) {
        this.orderId = orderId;
        this.tableNumber = tableNumber;
        this.itemNames = itemNames;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    public List<MenuItem> getItems() { return items; }
    public void setItems(List<MenuItem> items) { this.items = items; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<String> getItemNames() { return itemNames; }
    public void setItemNames(List<String> itemNames) { this.itemNames = itemNames; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}



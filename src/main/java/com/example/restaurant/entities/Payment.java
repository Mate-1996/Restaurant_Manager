package com.example.restaurant.entities;

import com.example.restaurant.interfaces.BillCalculable;

public class Payment implements BillCalculable {
    private int paymentId;
    private int orderId;
    private double amount;
    private String paymentMethod; // Cash, Card, etc.

    public Payment() {}

    public Payment(int paymentId, int orderId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    @Override
    public double calculateBill() {
        return amount; // (later we can improve if you want taxes, tips, etc.)
    }

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}

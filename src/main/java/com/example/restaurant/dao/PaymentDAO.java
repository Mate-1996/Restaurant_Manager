package com.example.restaurant.dao;

import com.example.restaurant.entities.Payment;
import com.example.restaurant.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public int processPayment(Payment payment) {
        String sql = "INSERT INTO Payment (order_id, amount, payment_method) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        int paymentId = -1;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, new String[]{"payment_id"});
            stmt.setInt(1, payment.getOrderId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.executeUpdate();

            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                paymentId = rs.getInt(1);
                payment.setPaymentId(paymentId);
            }

            // Update order status to paid
            String updateOrderSql = "UPDATE Orders SET status = 'Paid' WHERE order_id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateOrderSql)) {
                updateStmt.setInt(1, payment.getOrderId());
                updateStmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error processing payment: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return paymentId;
    }

    public List<Payment> getPaymentsByOrderId(int orderId) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT payment_id, order_id, amount, payment_method FROM Payment WHERE order_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                payments.add(new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("order_id"),
                        rs.getDouble("amount"),
                        rs.getString("payment_method")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error retrieving payments: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return payments;
    }
}

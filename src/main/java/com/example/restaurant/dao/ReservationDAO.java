package com.example.restaurant.dao;

import com.example.restaurant.entities.Reservation;
import com.example.restaurant.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationDAO {

    public boolean createReservation(String customerName, int tableNumber, LocalDateTime reservationTime) {
        String sql = "INSERT INTO Reservation (customer_name, table_number, reservation_time) VALUES (?, ?, ?)";
        boolean success = false;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, customerName);
            stmt.setInt(2, tableNumber);

            // Oracle requires a different way to handle timestamps
            stmt.setTimestamp(3, Timestamp.valueOf(reservationTime));

            int rowsAffected = stmt.executeUpdate();
            success = (rowsAffected > 0);

            if (success) {
                System.out.println("Reservation created successfully for " + customerName);
                // Explicitly commit the transaction for Oracle
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            } else {
                System.out.println("Failed to create reservation - no rows affected");
            }

        } catch (Exception e) {
            try {
                // Rollback if there's an error
                if (conn != null && !conn.getAutoCommit()) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }

            e.printStackTrace();
            System.err.println("Error creating reservation: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }

        return success;
    }

    public List<Reservation> getAllDetailedReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT reservation_id, customer_name, table_number, reservation_time, created_at, order_summary FROM Reservation ORDER BY reservation_time DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String summary = rs.getString("order_summary");
                double totalPrice = calculateTotalPriceFromSummary(summary); // 🔁 Calculate total

                Reservation r = new Reservation(
                        rs.getInt("reservation_id"),
                        rs.getString("customer_name"),
                        rs.getInt("table_number"),
                        rs.getTimestamp("reservation_time").toLocalDateTime(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        summary
                );

                r.setTotalPrice(totalPrice); // set total price on entity
                reservations.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error retrieving reservations: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }

        return reservations;
    }


    // Additional helpful methods


    public double calculateTotalPriceFromSummary(String orderSummary) {
        double total = 0.0;

        if (orderSummary == null || orderSummary.isEmpty()) return 0.0;

        String[] items = orderSummary.split(",");

        try (Connection conn = DatabaseConnection.getConnection()) {
            for (String item : items) {
                String[] parts = item.trim().split(" x");
                if (parts.length == 2) {
                    String itemName = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1]);

                    String sql = "SELECT price FROM MENU_ITEM WHERE name = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, itemName);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            double price = rs.getDouble("price");
                            total += price * quantity;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }


    public List<Map<String, Object>> getAllReservations() {
        List<Map<String, Object>> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservation ORDER BY reservation_time";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> reservation = new HashMap<>();
                reservation.put("id", rs.getInt("reservation_id"));
                reservation.put("customerName", rs.getString("customer_name"));
                reservation.put("tableNumber", rs.getInt("table_number"));
                reservation.put("reservationTime", rs.getTimestamp("reservation_time").toLocalDateTime());
                reservations.add(reservation);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error retrieving reservations: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }

        return reservations;
    }
}

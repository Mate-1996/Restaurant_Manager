package com.example.restaurant.dao;

import com.example.restaurant.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Timestamp;

public class ReservationDAO {

    public void createReservation(String customerName, int tableNumber, LocalDateTime reservationTime) {
        String sql = "INSERT INTO Reservation (customer_name, table_number, reservation_time) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerName);
            stmt.setInt(2, tableNumber);
            stmt.setTimestamp(3, Timestamp.valueOf(reservationTime));
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

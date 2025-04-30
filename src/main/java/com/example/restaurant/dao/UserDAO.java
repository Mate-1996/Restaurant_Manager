package com.example.restaurant.dao;

import com.example.restaurant.entities.Chef;
import com.example.restaurant.entities.User;
import com.example.restaurant.entities.Waiter;

import java.sql.*;
import java.util.*;

import com.example.restaurant.util.DatabaseConnection;

public class UserDAO {

    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                String name = rs.getString("name");

                if ("Waiter".equalsIgnoreCase(role)) {
                    return getWaiterById(userId, name);
                } else if ("Chef".equalsIgnoreCase(role)) {
                    return getChefById(userId, name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Waiter getWaiterById(int userId, String name) {
        List<Integer> tables = new ArrayList<>();
        String sql = "SELECT table_number FROM Waiter_Tables WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tables.add(rs.getInt("table_number"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Waiter(userId, name, tables);
    }

    private Chef getChefById(int userId, String name) {
        String sql = "SELECT station FROM Chef_Stations WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Chef(userId, name, rs.getString("station"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Chef(userId, name, "Unassigned");
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                users.add(getUserById(userId)); // re-use logic
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}

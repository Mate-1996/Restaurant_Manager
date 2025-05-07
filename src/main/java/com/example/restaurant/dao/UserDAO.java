package com.example.restaurant.dao;

import com.example.restaurant.entities.User;
import com.example.restaurant.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createUser(User user) {
        String sql = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // 🔐 hash in future
            stmt.setString(3, user.getRole());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User authenticate(String username, String password) {
        String sql = "SELECT user_id, role FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        username,
                        password,
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY user_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}


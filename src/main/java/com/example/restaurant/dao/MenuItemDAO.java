package com.example.restaurant.dao;

import com.example.restaurant.entities.MenuItem;
import com.example.restaurant.util.DatabaseConnection;
import com.example.restaurant.interfaces.DatabaseOperations;
import com.example.restaurant.exceptions.DatabaseConnectionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO implements DatabaseOperations<MenuItem> {

    @Override
    public void add(MenuItem item) {
        // Implementation for adding a menu item
    }

    @Override
    public void update(MenuItem item) {
        // Implementation for updating a menu item
    }

    @Override
    public void delete(int id) {
        // Implementation for deleting a menu item
    }

    @Override
    public List<MenuItem> getAll() {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT item_id, name, description, price FROM Menu_Item";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                items.add(new MenuItem(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error retrieving menu items: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return items;
    }
}



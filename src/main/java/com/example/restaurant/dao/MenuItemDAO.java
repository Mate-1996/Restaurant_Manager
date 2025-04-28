package com.example.restaurant.dao;

import com.example.restaurant.entities.MenuItem;
import com.example.restaurant.util.DatabaseConnection;
import com.example.restaurant.interfaces.DatabaseOperations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO implements DatabaseOperations<MenuItem> {

    @Override
    public void add(MenuItem item) {
        // TODO: Add menu item to database
    }

    @Override
    public void update(MenuItem item) {
        // TODO: Update menu item in database
    }

    @Override
    public void delete(int id) {
        // TODO: Delete menu item from database
    }

    @Override
    public List<MenuItem> getAll() {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT item_id, name, description, price FROM Menu_Item";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
        }

        return items;
    }
}



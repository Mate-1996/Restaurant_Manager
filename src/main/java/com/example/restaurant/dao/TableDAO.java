package com.example.restaurant.dao;

import com.example.restaurant.entities.Table;
import com.example.restaurant.util.DatabaseConnection;
import com.example.restaurant.interfaces.DatabaseOperations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDAO implements DatabaseOperations<Table> {

    @Override
    public void add(Table table) {
        String sql = "INSERT INTO Restaurant_Table (table_number, capacity, status) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, table.getTableNumber());
            stmt.setInt(2, table.getCapacity());
            stmt.setString(3, table.getStatus());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error adding table: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    @Override
    public void update(Table table) {
        String sql = "UPDATE Restaurant_Table SET capacity = ?, status = ? WHERE table_number = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, table.getCapacity());
            stmt.setString(2, table.getStatus());
            stmt.setInt(3, table.getTableNumber());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error updating table: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    @Override
    public void delete(int tableNumber) {
        String sql = "DELETE FROM Restaurant_Table WHERE table_number = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tableNumber);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error deleting table: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Table> getAll() {
        List<Table> tables = new ArrayList<>();
        String sql = "SELECT table_number, capacity, status FROM Restaurant_Table";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                tables.add(new Table(
                        rs.getInt("table_number"),
                        rs.getInt("capacity"),
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error retrieving tables: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return tables;
    }

    public Table getByNumber(int tableNumber) {
        String sql = "SELECT table_number, capacity, status FROM Restaurant_Table WHERE table_number = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Table table = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tableNumber);
            rs = stmt.executeQuery();

            if (rs.next()) {
                table = new Table(
                        rs.getInt("table_number"),
                        rs.getInt("capacity"),
                        rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error retrieving table: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return table;
    }

    public void updateTableStatus(int tableNumber, String status) {
        String sql = "UPDATE Restaurant_Table SET status = ? WHERE table_number = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, tableNumber);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error updating table status: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
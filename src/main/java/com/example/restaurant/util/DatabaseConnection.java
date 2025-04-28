package com.example.restaurant.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.restaurant.exceptions.DatabaseConnectionException;

public class DatabaseConnection {

    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521/FREEPDB1";
    private static final String USERNAME = "restaurant_user";
    private static final String PASSWORD = "restaurant_pass";

    public static Connection getConnection() throws DatabaseConnectionException {
        try {
            // Register the Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");

            // Create a connection
            Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Test if connection is valid
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Failed to establish database connection");
            }

            return conn;
        } catch (ClassNotFoundException e) {
            throw new DatabaseConnectionException("Oracle JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to connect to database: " + e.getMessage());
        }
    }

    // Method to close connection safely
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}

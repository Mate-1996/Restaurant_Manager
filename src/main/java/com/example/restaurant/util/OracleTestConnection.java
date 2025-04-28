package com.example.restaurant.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class OracleTestConnection {

    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521/FREEPDB1",
                    "restaurant_user",
                    "restaurant_pass"
            );
            System.out.println("Connected to Oracle database successfully!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

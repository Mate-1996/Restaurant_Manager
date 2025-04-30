package com.example.restaurant.servlets;

import com.example.restaurant.util.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

@WebServlet(name = "CreateUserServlet", value = "/create-user-servlet")
public class CreateUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String role = request.getParameter("role");
        String station = request.getParameter("station");
        String tables = request.getParameter("tables");

        try (Connection conn = DatabaseConnection.getConnection()) {

            // 1. Insert into Users table
            String userSql = "INSERT INTO Users (name, role) VALUES (?, ?)";
            PreparedStatement userStmt = conn.prepareStatement(userSql, new String[]{"user_id"});
            userStmt.setString(1, name);
            userStmt.setString(2, role);
            userStmt.executeUpdate();

            ResultSet rs = userStmt.getGeneratedKeys();
            int userId = -1;
            if (rs.next()) {
                userId = rs.getInt(1);
            }

            if ("Chef".equalsIgnoreCase(role) && station != null && !station.isBlank()) {
                String chefSql = "INSERT INTO Chef_Stations (user_id, station) VALUES (?, ?)";
                PreparedStatement chefStmt = conn.prepareStatement(chefSql);
                chefStmt.setInt(1, userId);
                chefStmt.setString(2, station);
                chefStmt.executeUpdate();
            }

            if ("Waiter".equalsIgnoreCase(role) && tables != null && !tables.isBlank()) {
                String[] tableNumbers = tables.split(",");
                String waiterSql = "INSERT INTO Waiter_Tables (user_id, table_number) VALUES (?, ?)";
                PreparedStatement waiterStmt = conn.prepareStatement(waiterSql);

                for (String t : tableNumbers) {
                    int tableNum = Integer.parseInt(t.trim());
                    waiterStmt.setInt(1, userId);
                    waiterStmt.setInt(2, tableNum);
                    waiterStmt.executeUpdate();
                }
            }

            request.setAttribute("message", "User created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Error creating user: " + e.getMessage());
        }

        request.getRequestDispatcher("/create-user.jsp").forward(request, response);
    }
}

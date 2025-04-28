package com.example.restaurant.servlets;

import com.example.restaurant.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@WebServlet(name = "TestDatabaseServlet", value = "/test-database")
public class TestDatabaseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        var out = response.getWriter();

        try {
            out.println("<html><body>");
            out.println("<h1>Database Connection Test</h1>");

            Connection conn = DatabaseConnection.getConnection();
            DatabaseMetaData metaData = conn.getMetaData();

            out.println("<p>Connection successful!</p>");
            out.println("<p>Database: " + metaData.getDatabaseProductName() + " " +
                    metaData.getDatabaseProductVersion() + "</p>");
            out.println("<p>Driver: " + metaData.getDriverName() + " " +
                    metaData.getDriverVersion() + "</p>");

            DatabaseConnection.closeConnection(conn);

            out.println("</body></html>");
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
            out.println("</body></html>");
        }
    }
}
package com.example.restaurant.servlets;

import com.example.restaurant.dao.TableDAO;
import com.example.restaurant.entities.Table;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "TableServlet", value = "/table-servlet")
public class TableServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TableDAO dao = new TableDAO();
        request.setAttribute("tables", dao.getAll());
        request.getRequestDispatcher("/tables.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        TableDAO dao = new TableDAO();

        if ("add".equals(action)) {
            int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            String status = request.getParameter("status");

            Table newTable = new Table(tableNumber, capacity, status);
            dao.add(newTable);
            request.setAttribute("message", "Table added successfully!");

        } else if ("update".equals(action)) {
            int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            String status = request.getParameter("status");

            Table table = new Table(tableNumber, capacity, status);
            dao.update(table);
            request.setAttribute("message", "Table updated successfully!");

        } else if ("delete".equals(action)) {
            int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
            dao.delete(tableNumber);
            request.setAttribute("message", "Table deleted successfully!");
        }

        request.setAttribute("tables", dao.getAll());
        request.getRequestDispatcher("/tables.jsp").forward(request, response);
    }
}
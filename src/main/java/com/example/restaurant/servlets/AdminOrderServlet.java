package com.example.restaurant.servlets;

import com.example.restaurant.dao.OrderDAO;
import com.example.restaurant.entities.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminOrderServlet", value = "/admin-order-servlet")
public class AdminOrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderDAO dao = new OrderDAO();
        List<Order> orders = dao.getAllOrdersWithItems();

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/admin-orders.jsp").forward(request, response);
    }
}



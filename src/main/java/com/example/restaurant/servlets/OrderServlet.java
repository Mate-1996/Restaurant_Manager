package com.example.restaurant.servlets;

import com.example.restaurant.dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "OrderServlet", value = "/order-servlet")
public class OrderServlet extends HttpServlet {



    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tableNumberStr = request.getParameter("tableNumber");
        String[] menuItemIds = request.getParameterValues("menuItemIds");

        if (tableNumberStr != null && menuItemIds != null) {
            int tableNumber = Integer.parseInt(tableNumberStr);

            OrderDAO orderDAO = new OrderDAO();
            int orderId = orderDAO.createOrder(tableNumber);

            for (String menuItemIdStr : menuItemIds) {
                int itemId = Integer.parseInt(menuItemIdStr);
                orderDAO.addItemToOrder(orderId, itemId);
            }

            request.setAttribute("message", "Order placed successfully for Table #" + tableNumber + "!");
        } else {
            request.setAttribute("message", "Please select items to order.");
        }

        request.getRequestDispatcher("/order-success.jsp").forward(request, response);
    }
}


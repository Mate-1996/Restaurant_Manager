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
            try {
                int tableNumber = Integer.parseInt(tableNumberStr);

                OrderDAO orderDAO = new OrderDAO();
                int orderId = orderDAO.createOrder(tableNumber);

                for (String itemIdStr : menuItemIds) {
                    int itemId = Integer.parseInt(itemIdStr);

                    // Read quantity input like: quantity_3
                    String quantityParam = request.getParameter("quantity_" + itemIdStr);
                    int quantity = 1;
                    if (quantityParam != null && !quantityParam.isEmpty()) {
                        try {
                            quantity = Integer.parseInt(quantityParam);
                        } catch (NumberFormatException e) {
                            quantity = 1;
                        }
                    }

                    orderDAO.addItemToOrder(orderId, itemId, quantity);
                }

                request.setAttribute("message", "Order placed successfully for Table #" + tableNumber + "!");

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Error placing order: " + e.getMessage());
            }
        } else {
            request.setAttribute("message", "Please select items to order.");
        }

        request.getRequestDispatcher("/order-success.jsp").forward(request, response);
    }
}


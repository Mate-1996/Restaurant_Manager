package com.example.restaurant.servlets;

import com.example.restaurant.dao.OrderDAO;
import com.example.restaurant.dao.ReservationDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "OrderReservationServlet", value = "/order-reservation-servlet")
public class OrderReservationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        com.example.restaurant.dao.MenuItemDAO menuDAO = new com.example.restaurant.dao.MenuItemDAO();
        com.example.restaurant.dao.TableDAO tableDAO = new com.example.restaurant.dao.TableDAO();

        request.setAttribute("menuItems", menuDAO.getAll());
        request.setAttribute("tables", tableDAO.getAvailableTables());


        request.getRequestDispatcher("/order-and-reserve.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String customerName = request.getParameter("customerName");
        String tableNumberStr = request.getParameter("tableNumber");
        String reservationTimeStr = request.getParameter("reservationTime");
        String[] menuItemIds = request.getParameterValues("menuItemIds");

        if (customerName != null && tableNumberStr != null && reservationTimeStr != null) {
            try {
                int tableNumber = Integer.parseInt(tableNumberStr);

                // Parse datetime
                LocalDateTime reservationTime = LocalDateTime.parse(reservationTimeStr);

                // Save reservation
                ReservationDAO reservationDAO = new ReservationDAO();
                reservationDAO.createReservation(customerName, tableNumber, reservationTime);

                // Mark the table as Reserved
                com.example.restaurant.dao.TableDAO tableDAO = new com.example.restaurant.dao.TableDAO();
                tableDAO.updateTableStatus(tableNumber, "Reserved");


                // Save order only if items were selected
                if (menuItemIds != null && menuItemIds.length > 0) {
                    OrderDAO orderDAO = new OrderDAO();
                    int orderId = orderDAO.createOrder(tableNumber);

                    for (String itemIdStr : menuItemIds) {
                        int itemId = Integer.parseInt(itemIdStr);
                        String quantityParam = request.getParameter("quantity_" + itemIdStr);
                        int quantity = 1; // default if missing

                        if (quantityParam != null && !quantityParam.isEmpty()) {
                            try {
                                quantity = Integer.parseInt(quantityParam);
                            } catch (NumberFormatException e) {
                                quantity = 1; // fallback safely
                            }
                        }

                        orderDAO.addItemToOrder(orderId, itemId, quantity); // NEW: passes quantity
                    }

                    request.setAttribute("message", "Reservation and Order placed successfully!");
                } else {
                    request.setAttribute("message", "Reservation placed successfully. No items were ordered.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Error processing reservation/order: " + e.getMessage());
            }
        } else {
            request.setAttribute("message", "Missing required fields.");
        }

        // Reload menu in case of message
        com.example.restaurant.dao.MenuItemDAO dao = new com.example.restaurant.dao.MenuItemDAO();
        request.setAttribute("menuItems", dao.getAll());

        request.getRequestDispatcher("/order-and-reserve.jsp").forward(request, response);
    }
}


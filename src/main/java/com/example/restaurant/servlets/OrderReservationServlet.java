
package com.example.restaurant.servlets;

import com.example.restaurant.dao.OrderDAO;
import com.example.restaurant.dao.ReservationDAO;
import com.example.restaurant.dao.TableDAO;
import com.example.restaurant.dao.MenuItemDAO;
import com.example.restaurant.entities.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "OrderReservationServlet", value = "/order-reservation-servlet")
public class OrderReservationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        populateRequestAttributes(request);
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
                LocalDateTime reservationTime = LocalDateTime.parse(reservationTimeStr);

                Map<Integer, Integer> itemQuantities = new HashMap<>();

                if (menuItemIds != null && menuItemIds.length > 0) {
                    for (String itemIdStr : menuItemIds) {
                        int itemId = Integer.parseInt(itemIdStr);
                        String quantityStr = request.getParameter("quantity_" + itemIdStr);
                        int quantity = 1;
                        if (quantityStr != null && !quantityStr.isEmpty()) {
                            try {
                                quantity = Integer.parseInt(quantityStr);
                            } catch (NumberFormatException e) {
                                quantity = 1;
                            }
                        }
                        itemQuantities.put(itemId, quantity);
                    }
                }

                // Create reservation with summary
                OrderDAO dao = new OrderDAO();
                int reservationId = dao.createReservationWithItems(customerName, tableNumber, reservationTime, itemQuantities);

                // Update table status
                TableDAO tableDAO = new TableDAO();
                tableDAO.updateTableStatus(tableNumber, "Reserved");

                if (reservationId > 0) {
                    request.setAttribute("message", "Reservation and Order placed successfully!");
                    request.setAttribute("reservationSuccess", true);
                    request.setAttribute("customerName", customerName);
                    request.setAttribute("tableNumber", tableNumber);
                    request.setAttribute("reservationTime", reservationTime);
                } else {
                    request.setAttribute("message", "Reservation failed.");
                    request.setAttribute("error", true);
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Error processing reservation/order: " + e.getMessage());
                request.setAttribute("error", true);
            }
        } else {
            request.setAttribute("message", "Missing required fields.");
            request.setAttribute("error", true);
        }

        populateRequestAttributes(request);
        request.getRequestDispatcher("/order-and-reserve.jsp").forward(request, response);
    }

    private void populateRequestAttributes(HttpServletRequest request) {

        MenuItemDAO menuDAO = new MenuItemDAO();
        TableDAO tableDAO = new TableDAO();

        request.setAttribute("menuItems", menuDAO.getAll());
        request.setAttribute("tables", tableDAO.getAll());
        request.setAttribute("availableTables", tableDAO.getAvailableTables());
    }
}


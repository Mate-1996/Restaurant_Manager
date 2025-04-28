package com.example.restaurant.servlets;

import com.example.restaurant.dao.ReservationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "ReservationServlet", value = "/reservation-servlet")
public class ReservationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String customerName = request.getParameter("customerName");
        String tableNumberStr = request.getParameter("tableNumber");
        String reservationTimeStr = request.getParameter("reservationTime");

        if (customerName != null && tableNumberStr != null && reservationTimeStr != null) {
            int tableNumber = Integer.parseInt(tableNumberStr);
            LocalDateTime reservationTime = LocalDateTime.parse(reservationTimeStr.replace("T", "T") + ":00");

            ReservationDAO reservationDAO = new ReservationDAO();
            reservationDAO.createReservation(customerName, tableNumber, reservationTime);

            request.setAttribute("message", "Reservation made successfully for " + customerName + "!");
        } else {
            request.setAttribute("message", "Please fill out all fields.");
        }

        request.getRequestDispatcher("/reservation-success.jsp").forward(request, response);
    }
}


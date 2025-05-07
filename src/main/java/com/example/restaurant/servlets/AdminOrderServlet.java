package com.example.restaurant.servlets;

import com.example.restaurant.dao.ReservationDAO;
import com.example.restaurant.entities.Reservation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminOrderServlet", value = "/admin-order-servlet")
public class AdminOrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ReservationDAO dao = new ReservationDAO();
        List<Reservation> reservations = dao.getAllDetailedReservations();


        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/admin-orders.jsp").forward(request, response);
    }
}


package com.example.restaurant.servlets;

import com.example.restaurant.dao.UserDAO;
import com.example.restaurant.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "UserRoutingServlet", value = "/user-router")
public class UserRoutingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        UserDAO dao = new UserDAO();
        User user = dao.getUserById(userId);

        if (user != null) {
            request.setAttribute("user", user);

            switch (user.getRole().toLowerCase()) {
                case "waiter":
                    request.getRequestDispatcher("/waiter-dashboard.jsp").forward(request, response);
                    break;
                case "chef":
                    request.getRequestDispatcher("/chef-dashboard.jsp").forward(request, response);
                    break;
                default:
                    response.getWriter().write("Unknown role.");
            }
        } else {
            response.getWriter().write("User not found.");
        }
    }
}

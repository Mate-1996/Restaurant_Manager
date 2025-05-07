package com.example.restaurant.servlets;

import com.example.restaurant.dao.UserDAO;
import com.example.restaurant.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.authenticate(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            // Redirect based on role
            switch (user.getRole()) {
                case "admin":
                    response.sendRedirect("admin-order-servlet");
                    break;
                case "waiter":
                    response.sendRedirect("waiter-dashboard.jsp");
                    break;
                case "user":
                    response.sendRedirect("order-and-reserve.jsp");
                    break;
                default:
                    response.sendRedirect("index.jsp");
                    break;
            }

        } else {
            request.setAttribute("message", "Invalid username or password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}


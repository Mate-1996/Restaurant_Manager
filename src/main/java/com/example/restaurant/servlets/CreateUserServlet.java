package com.example.restaurant.servlets;

import com.example.restaurant.util.DatabaseConnection;

import com.example.restaurant.dao.UserDAO;
import com.example.restaurant.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "CreateUserServlet", value = "/create-user-servlet")
public class CreateUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        UserDAO userDAO = new UserDAO();

        // ✅ Check if username exists
        if (userDAO.userExists(username)) {
            request.setAttribute("error", "Username already exists.");
            request.getRequestDispatcher("/create-user.jsp").forward(request, response);
            return;
        }

        // ✅ Create new user using entity
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // 🔐 Add password hashing later
        user.setRole(role);

        if (userDAO.createUser(user)) {
            request.setAttribute("message", "User created successfully!");
        } else {
            request.setAttribute("error", "Error creating user.");
        }

        request.getRequestDispatcher("/create-user.jsp").forward(request, response);
    }
}

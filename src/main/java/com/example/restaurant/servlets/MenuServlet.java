package com.example.restaurant.servlets;

import com.example.restaurant.dao.MenuItemDAO;
import com.example.restaurant.entities.MenuItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MenuServlet", value = "/menu-servlet")
public class MenuServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MenuItemDAO dao = new MenuItemDAO();
        List<MenuItem> menuItems = dao.getAll();

        request.setAttribute("menuItems", menuItems);
        request.getRequestDispatcher("/menu.jsp").forward(request, response);
    }
}

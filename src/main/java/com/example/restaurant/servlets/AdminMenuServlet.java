package com.example.restaurant.servlets;

import com.example.restaurant.dao.MenuItemDAO;
import com.example.restaurant.entities.MenuItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AdminMenuServlet", value = "/admin-menu-servlet")
public class AdminMenuServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MenuItemDAO dao = new MenuItemDAO();
        request.setAttribute("menuItems", dao.getAll());
        request.getRequestDispatcher("/admin-menu.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        MenuItemDAO dao = new MenuItemDAO();

        if ("add".equals(action)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));

            MenuItem newItem = new MenuItem();
            newItem.setName(name);
            newItem.setDescription(description);
            newItem.setPrice(price);

            dao.add(newItem);
            request.setAttribute("message", "Menu item added successfully!");

        } else if ("update".equals(action)) {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));

            MenuItem item = new MenuItem();
            item.setItemId(itemId);
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);

            dao.update(item);
            request.setAttribute("message", "Menu item updated successfully!");

        } else if ("delete".equals(action)) {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            dao.delete(itemId);
            request.setAttribute("message", "Menu item deleted successfully!");
        }

        request.setAttribute("menuItems", dao.getAll());
        request.getRequestDispatcher("/admin-menu.jsp").forward(request, response);
    }
}

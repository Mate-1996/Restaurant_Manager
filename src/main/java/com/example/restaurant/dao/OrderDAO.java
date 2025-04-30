package com.example.restaurant.dao;

import com.example.restaurant.entities.MenuItem;
import com.example.restaurant.entities.Order;
import com.example.restaurant.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public int createOrder(int tableNumber) {
        int orderId = -1;
        String sql = "INSERT INTO Orders (table_number, status) VALUES (?, 'Pending')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"order_id"})) {

            stmt.setInt(1, tableNumber);
            stmt.executeUpdate();

            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // Update table status
            TableDAO tableDAO = new TableDAO();
            tableDAO.updateTableStatus(tableNumber, "Occupied");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderId;
    }

    public void addItemToOrder(int orderId, int itemId, int quantity) {
        String sql = "INSERT INTO ORDER_ITEM (order_id, item_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, itemId);
            stmt.setInt(3, quantity);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrdersWithItems() {
        List<Order> orders = new ArrayList<>();

        String orderSql = "SELECT * FROM ORDERS";
        String itemSql = """
        SELECT mi.name, mi.price, oi.quantity
        FROM ORDER_ITEM oi
        JOIN MENU_ITEM mi ON oi.item_id = mi.item_id
        WHERE oi.order_id = ?
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement orderStmt = conn.prepareStatement(orderSql);
             ResultSet orderRs = orderStmt.executeQuery()) {

            while (orderRs.next()) {
                int orderId = orderRs.getInt("order_id");
                int tableNumber = orderRs.getInt("table_number");

                List<String> itemDisplayList = new ArrayList<>();
                double totalPrice = 0.0;

                try (PreparedStatement itemStmt = conn.prepareStatement(itemSql)) {
                    itemStmt.setInt(1, orderId);
                    ResultSet itemRs = itemStmt.executeQuery();

                    while (itemRs.next()) {
                        String name = itemRs.getString("name");
                        double price = itemRs.getDouble("price");
                        int quantity = itemRs.getInt("quantity");

                        itemDisplayList.add(name + " x" + quantity);
                        totalPrice += price * quantity;
                    }
                }

                orders.add(new Order(orderId, tableNumber, itemDisplayList, totalPrice));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error fetching orders: " + e.getMessage());
        }

        return orders;
    }


    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT order_id, table_number, status FROM Orders";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                Order order = new Order(
                        orderId,
                        rs.getInt("table_number"),
                        getOrderItems(orderId, conn),
                        rs.getString("status")
                );
                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT order_id, table_number, status FROM Orders WHERE order_id = ?";
        Order order = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("table_number"),
                        getOrderItems(orderId, conn),
                        rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }

    public List<Order> getOrdersByStatus(String status) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT order_id, table_number, status FROM Orders WHERE status = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                Order order = new Order(
                        orderId,
                        rs.getInt("table_number"),
                        getOrderItems(orderId, conn),
                        rs.getString("status")
                );
                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public void updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE Orders SET status = ? WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();

            // If order is completed, update table status
            if (status.equals("Completed")) {
                // Get the table number first
                String getTableSql = "SELECT table_number FROM Orders WHERE order_id = ?";
                try (PreparedStatement getTableStmt = conn.prepareStatement(getTableSql)) {
                    getTableStmt.setInt(1, orderId);
                    ResultSet rs = getTableStmt.executeQuery();
                    if (rs.next()) {
                        int tableNumber = rs.getInt("table_number");
                        TableDAO tableDAO = new TableDAO();
                        tableDAO.updateTableStatus(tableNumber, "Available");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<MenuItem> getOrderItems(int orderId, Connection conn) throws SQLException {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT m.item_id, m.name, m.description, m.price, oi.quantity " +
                "FROM Menu_Item m JOIN Order_Item oi ON m.item_id = oi.item_id " +
                "WHERE oi.order_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MenuItem item = new MenuItem(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price")
                );
                item.setQuantity(rs.getInt("quantity"));
                items.add(item);
            }
        }

        return items;
    }

    public double calculateOrderTotal(int orderId) {
        double total = 0.0;
        String sql = "SELECT m.price, oi.quantity " +
                "FROM Menu_Item m JOIN Order_Item oi ON m.item_id = oi.item_id " +
                "WHERE oi.order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                total += price * quantity;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public double calculateOrderTotalWithTax(int orderId) {
        double subtotal = calculateOrderTotal(orderId);
        double taxRate = 0.08; // 8% tax rate
        return subtotal * (1 + taxRate);
    }
}

<%@ page import="java.util.List" %>
<%@ page import="com.example.restaurant.entities.Order" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>All Orders</title>
  <link rel="stylesheet" href="css/style.css">
  <style>
    body { font-family: Arial; background: #f4f4f4; }
    .container { width: 80%; margin: 30px auto; background: white; padding: 20px; border-radius: 10px; }
    h1 { text-align: center; }
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th, td { border: 1px solid #ccc; padding: 12px; text-align: center; }
    th { background-color: #3f51b5; color: white; }
  </style>
</head>
<body>
<div class="container">
  <h1>Customer Orders</h1>

  <table>
    <thead>
    <tr>
      <th>Order ID</th>
      <th>Table Number</th>
      <th>Items</th>
      <th>Total Price ($)</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<Order> orders = (List<Order>) request.getAttribute("orders");
      if (orders != null && !orders.isEmpty()) {
        for (Order order : orders) {
    %>
    <tr>
      <td><%= order.getOrderId() %></td>
      <td><%= order.getTableNumber() %></td>
      <td><%= String.join(", ", order.getItemNames()) %></td>
      <td>$<%= String.format("%.2f", order.getTotalPrice()) %></td>
    </tr>
    <%
      }
    } else {
    %>
    <tr><td colspan="4">No orders found.</td></tr>
    <%
      }
    %>
    </tbody>
  </table>

  <br>
  <a href="index.jsp" class="button">Back to Home</a>
</div>
</body>
</html>


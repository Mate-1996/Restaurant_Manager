<%@ page import="java.util.List" %>
<%@ page import="com.example.restaurant.entities.Reservation" %>

<!DOCTYPE html>
<html>
<head>
  <title>Admin - Reservations Overview</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background: #f9f9f9;
      margin: 0;
      padding: 20px;
    }
    .container {
      width: 95%;
      margin: auto;
      background: white;
      padding: 20px;
      border-radius: 10px;
      box-shadow: 0 0 10px #ccc;
    }
    h1 {
      text-align: center;
      margin-bottom: 30px;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 10px;
    }
    th, td {
      padding: 12px;
      border: 1px solid #ddd;
      text-align: center;
    }
    th {
      background-color: #333;
      color: white;
    }
    a.button {
      display: inline-block;
      margin-top: 20px;
      padding: 10px 20px;
      background: #3f51b5;
      color: white;
      text-decoration: none;
      border-radius: 5px;
    }
    a.button:hover {
      background: #303f9f;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Reservation Orders Overview</h1>

  <table>
    <thead>
    <tr>
      <th>Reservation ID</th>
      <th>Customer Name</th>
      <th>Table Number</th>
      <th>Reservation Time</th>
      <th>Ordered Items (with Quantities)</th>
      <th>Created At</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
      if (reservations != null && !reservations.isEmpty()) {
        for (Reservation r : reservations) {
    %>
    <tr>
      <td><%= r.getReservationId() %></td>
      <td><%= r.getCustomerName() %></td>
      <td><%= r.getTableNumber() %></td>
      <td><%= r.getReservationTime() != null ? r.getReservationTime() : "-" %></td>
      <td><%= r.getOrderSummary() != null ? r.getOrderSummary() : "None" %></td>
      <td><%= r.getCreatedAt() != null ? r.getCreatedAt() : "-" %></td>
    </tr>
    <%
      }
    } else {
    %>
    <tr><td colspan="6">No reservation data available.</td></tr>
    <%
      }
    %>
    </tbody>
  </table>

  <a class="button" href="index.jsp">Back to Home</a>
</div>
</body>
</html>


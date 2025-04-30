<%@ page import="java.util.List" %>
<%@ page import="com.example.restaurant.entities.Table" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Manage Tables</title>
  <link rel="stylesheet" href="css/style.css">
  <style>
    table {
      width: 80%;
      margin: auto;
      border-collapse: collapse;
      margin-top: 30px;
    }
    th, td {
      padding: 12px;
      border: 1px solid #ddd;
      text-align: center;
    }
    th {
      background-color: #3f51b5;
      color: white;
    }
    .form-section {
      margin: 30px auto;
      width: 50%;
      text-align: left;
      background: #f9f9f9;
      padding: 20px;
      border-radius: 10px;
    }
    .form-section input[type="text"],
    .form-section input[type="number"] {
      width: 95%;
      padding: 10px;
      margin: 5px 0 15px 0;
      border: 1px solid #ccc;
      border-radius: 5px;
    }
    .form-section button {
      background-color: #4CAF50;
      color: white;
      padding: 12px 25px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }
    .form-section button:hover {
      background-color: #45a049;
    }
    .button-delete {
      background-color: #e53935;
      color: white;
      padding: 7px 15px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }
    .button-delete:hover {
      background-color: #c62828;
    }
  </style>
</head>
<body>

<div class="container">
  <h1>Manage Tables</h1>

  <%
    String message = (String) request.getAttribute("message");
    if (message != null) {
  %>
  <p style="color: green;"><%= message %></p>
  <%
    }
  %>

  <!-- Table List -->
  <table>
    <thead>
    <tr>
      <th>Table Number</th>
      <th>Capacity</th>
      <th>Status</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<Table> tables = (List<Table>) request.getAttribute("tables");
      if (tables != null && !tables.isEmpty()) {
        for (Table table : tables) {
    %>
    <tr>
      <td><%= table.getTableNumber() %></td>
      <td><%= table.getCapacity() %></td>
      <td><%= table.getStatus() %></td>
      <td>
        <!-- Update Form -->
        <form action="table-servlet" method="post" style="display:inline;">
          <input type="hidden" name="action" value="update">
          <input type="hidden" name="tableNumber" value="<%= table.getTableNumber() %>">
          <input type="number" name="capacity" value="<%= table.getCapacity() %>" required>
          <input type="text" name="status" value="<%= table.getStatus() %>" required>
          <button type="submit">Update</button>
        </form>

        <!-- Delete Form -->
        <form action="table-servlet" method="post" style="display:inline;">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="tableNumber" value="<%= table.getTableNumber() %>">
          <button type="submit" class="button-delete">Delete</button>
        </form>
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr><td colspan="4">No tables found.</td></tr>
    <%
      }
    %>
    </tbody>
  </table>

  <!-- Add New Table Section -->
  <div class="form-section">
    <h2>Add New Table</h2>
    <form action="table-servlet" method="post">
      <input type="hidden" name="action" value="add">

      <label>Table Number:</label>
      <input type="number" name="tableNumber" required>

      <label>Capacity:</label>
      <input type="number" name="capacity" required>

      <label>Status:</label>
      <input type="text" name="status" required>

      <button type="submit">Add Table</button>
    </form>
  </div>

  <br>
  <a href="index.jsp" class="button">Back to Home</a>

</div>

</body>
</html>


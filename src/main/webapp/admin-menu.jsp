<%@ page import="java.util.List" %>
<%@ page import="com.example.restaurant.entities.MenuItem" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Admin - Manage Menu</title>
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
      background-color: #f4511e;
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
    .form-section input[type="number"],
    .form-section textarea {
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
  <h1>Admin - Manage Menu Items</h1>

  <%
    String message = (String) request.getAttribute("message");
    if (message != null) {
  %>
  <p style="color: green;"><%= message %></p>
  <%
    }
  %>

  <!-- Table of Current Menu Items -->
  <table>
    <thead>
    <tr>
      <th>Item ID</th>
      <th>Name</th>
      <th>Description</th>
      <th>Price ($)</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<MenuItem> menuItems = (List<MenuItem>) request.getAttribute("menuItems");
      if (menuItems != null && !menuItems.isEmpty()) {
        for (MenuItem item : menuItems) {
    %>
    <tr>
      <td><%= item.getItemId() %></td>
      <td><%= item.getName() %></td>
      <td><%= item.getDescription() %></td>
      <td><%= item.getPrice() %></td>
      <td>
        <!-- Update Form -->
        <form action="admin-menu-servlet" method="post" style="display:inline;">
          <input type="hidden" name="action" value="update">
          <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
          <input type="text" name="name" value="<%= item.getName() %>" required>
          <input type="text" name="description" value="<%= item.getDescription() %>" required>
          <input type="number" name="price" value="<%= item.getPrice() %>" step="0.01" required>
          <button type="submit">Update</button>
        </form>

        <!-- Delete Form -->
        <form action="admin-menu-servlet" method="post" style="display:inline;">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
          <button type="submit" class="button-delete">Delete</button>
        </form>
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr><td colspan="5">No menu items found.</td></tr>
    <%
      }
    %>
    </tbody>
  </table>

  <!-- Add New Item Section -->
  <div class="form-section">
    <h2>Add New Menu Item</h2>
    <form action="admin-menu-servlet" method="post">
      <input type="hidden" name="action" value="add">
      <label>Name:</label>
      <input type="text" name="name" required>

      <label>Description:</label>
      <textarea name="description" rows="3" required></textarea>

      <label>Price ($):</label>
      <input type="number" name="price" step="0.01" required>

      <button type="submit">Add Item</button>
    </form>
  </div>

  <br>
  <a href="index.jsp" class="button">Back to Home</a>

</div>

</body>
</html>

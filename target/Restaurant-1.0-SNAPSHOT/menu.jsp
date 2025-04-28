<%@ page import="java.util.List" %>
<%@ page import="com.example.restaurant.entities.MenuItem" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Menu</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Our Menu</h1>
<ul>
    <%
        List<MenuItem> menuItems = (List<MenuItem>) request.getAttribute("menuItems");
        if (menuItems != null && !menuItems.isEmpty()) {
            for (MenuItem item : menuItems) {
    %>
    <li><b><%= item.getName() %></b> - $<%= item.getPrice() %> <br><small><%= item.getDescription() %></small></li>
    <%
        }
    } else {
    %>
    <li>No menu items found.</li>
    <%
        }
    %>
</ul>
</body>
</html>

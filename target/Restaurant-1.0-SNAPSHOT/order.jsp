<%@ page import="java.util.List" %>
<%@ page import="com.example.restaurant.entities.MenuItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Place an Order</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Place Your Order</h1>

<form action="order-servlet" method="post">
    <label for="tableNumber">Table Number:</label>
    <input type="number" id="tableNumber" name="tableNumber" required>

    <label>Choose Items:</label><br>
    <%
        List<MenuItem> menuItems = (List<MenuItem>) request.getAttribute("menuItems");
        if (menuItems != null) {
            for (MenuItem item : menuItems) {
    %>
    <input type="checkbox" name="menuItemIds" value="<%= item.getItemId() %>"> <%= item.getName() %> ($<%= item.getPrice() %>)<br>
    <%
            }
        }
    %>

    <br>
    <input type="submit" value="Place Order" class="button">
</form>

</body>
</html>


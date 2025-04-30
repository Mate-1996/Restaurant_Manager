<%@ page import="com.example.restaurant.entities.Waiter" %>
<%@ page import="java.util.List" %>
<%
  Waiter waiter = (Waiter) request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head><title>Waiter Dashboard</title></head>
<body>
<h1>Welcome, <%= waiter.getName() %> (Waiter)</h1>
<h2>Your Assigned Tables:</h2>
<ul>
  <% for (Integer table : waiter.getAssignedTables()) { %>
  <li>Table <%= table %></li>
  <% } %>
</ul>
</body>
</html>


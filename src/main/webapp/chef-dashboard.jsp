<%@ page import="com.example.restaurant.entities.Chef" %>
<%
  Chef chef = (Chef) request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head><title>Chef Dashboard</title></head>
<body>
<h1>Welcome, <%= chef.getName() %> (Chef)</h1>
<h2>Your Station: <%= chef.getKitchenStation() %></h2>
</body>
</html>


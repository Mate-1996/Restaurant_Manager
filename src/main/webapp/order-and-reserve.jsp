<%@ page import="java.util.List" %>
<%@ page import="com.example.restaurant.entities.MenuItem" %>
<%@ page import="com.example.restaurant.entities.Order" %>
<%@ page import="com.example.restaurant.entities.Table" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order & Reserve</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f4f4;
        }
        .container {
            width: 80%;
            margin: 30px auto;
            background: white;
            padding: 20px;
            border-radius: 12px;
        }
        h2 {
            color: #333;
            margin-top: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 6px;
        }
        input[type="text"],
        input[type="number"],
        input[type="datetime-local"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .menu-section {
            margin-top: 20px;
        }
        .menu-item {
            margin-bottom: 10px;
        }
        .button {
            background-color: #4CAF50;
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
        }
        .button:hover {
            background-color: #388e3c;
        }
        .confirmation {
            background-color: #e8f5e9;
            border: 1px solid #c8e6c9;
            padding: 15px;
            margin: 20px 0;
            border-radius: 5px;
        }
        .error {
            background-color: #ffebee;
            border: 1px solid #ffcdd2;
            padding: 15px;
            margin: 20px 0;
            border-radius: 5px;
        }
        .order-details {
            border: 1px solid #ddd;
            padding: 15px;
            margin-top: 20px;
            border-radius: 5px;
        }
        .order-item {
            margin: 10px 0;
            padding: 5px 0;
            border-bottom: 1px dashed #ddd;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Order Your Meal & Reserve a Table</h1>

    <% if (request.getAttribute("message") != null) { %>
    <div class="<%= request.getAttribute("error") != null ? "error" : "confirmation" %>">
        <%= request.getAttribute("message") %>
    </div>
    <% } %>

    <%

        if (request.getAttribute("reservationSuccess") != null && (Boolean)request.getAttribute("reservationSuccess")) {
            String customerName = (String)request.getAttribute("customerName");
            Integer tableNumber = (Integer)request.getAttribute("tableNumber");
            LocalDateTime reservationTime = (LocalDateTime)request.getAttribute("reservationTime");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    %>
    <div class="confirmation">
        <h2>Reservation Confirmation</h2>
        <p><strong>Customer Name:</strong> <%= customerName %></p>
        <p><strong>Table Number:</strong> <%= tableNumber %></p>
        <p><strong>Reservation Time:</strong> <%= reservationTime.format(formatter) %></p>

        <% if (request.getAttribute("order") != null) {
            Order order = (Order)request.getAttribute("order");
            Double orderTotal = (Double)request.getAttribute("orderTotal");
            Double orderTotalWithTax = (Double)request.getAttribute("orderTotalWithTax");
        %>
        <div class="order-details">
            <h3>Order Details (Order #<%= order.getOrderId() %>)</h3>

            <% if (order.getItems() != null && !order.getItems().isEmpty()) { %>
            <% for (MenuItem item : order.getItems()) { %>
            <div class="order-item">
                <strong><%= item.getName() %></strong> -
                Quantity: <%= item.getQuantity() %> -
                Price: $<%= String.format("%.2f", item.getPrice()) %> -
                Subtotal: $<%= String.format("%.2f", item.getPrice() * item.getQuantity()) %>
            </div>
            <% } %>

            <div style="margin-top: 15px; font-weight: bold;">
                <p>Subtotal: $<%= String.format("%.2f", orderTotal) %></p>
                <p>Total (including 8% tax): $<%= String.format("%.2f", orderTotalWithTax) %></p>
            </div>
            <% } %>
        </div>
        <% } %>
    </div>
    <% } %>

    <form action="order-reservation-servlet" method="post">

        <!-- Reservation Info -->
        <h2>Reservation Details</h2>

        <div class="form-group">
            <label for="customerName">Your Name:</label>
            <input type="text" id="customerName" name="customerName" required>
        </div>

        <div class="form-group">
            <label for="tableNumber">Choose a Table:</label>
            <select name="tableNumber" id="tableNumber" required>
                <%
                    List<Table> tables = (List<Table>) request.getAttribute("tables");
                    List<Table> availableTables = (List<Table>) request.getAttribute("availableTables");

                    if (availableTables != null && !availableTables.isEmpty()) {
                        for (Table table : availableTables) {
                %>
                <option value="<%= table.getTableNumber() %>">
                    Table <%= table.getTableNumber() %> (Capacity: <%= table.getCapacity() %>, Status: Available)
                </option>
                <%
                    }
                } else if (tables != null) {
                    // If no available tables, display all tables with status indicators
                    for (Table table : tables) {
                        boolean isAvailable = "Available".equals(table.getStatus());
                %>
                <option value="<%= table.getTableNumber() %>" <%= isAvailable ? "" : "disabled" %>>
                    Table <%= table.getTableNumber() %> (Capacity: <%= table.getCapacity() %>, Status: <%= table.getStatus() %>)
                </option>
                <%
                        }
                    }

                    if ((availableTables == null || availableTables.isEmpty()) &&
                            (tables == null || tables.isEmpty())) {
                %>
                <option value="" disabled>No tables available</option>
                <%
                    }
                %>
            </select>
        </div>

        <div class="form-group">
            <label for="reservationTime">Reservation Time:</label>
            <input type="datetime-local" id="reservationTime" name="reservationTime" required>
        </div>


        <h2>Menu</h2>

        <div class="menu-section">
            <%
                List<MenuItem> menuItems = (List<MenuItem>) request.getAttribute("menuItems");
                if (menuItems != null && !menuItems.isEmpty()) {
                    for (MenuItem item : menuItems) {
            %>
            <div class="menu-item">
                <input type="checkbox" class="menu-check" name="menuItemIds" value="<%= item.getItemId() %>" onchange="calculateTotal()">
                <label>
                    <strong><%= item.getName() %></strong> - $<%= String.format("%.2f", item.getPrice()) %><br/>
                    <small><%= item.getDescription() %></small>
                </label>
                <br/>
                Quantity:
                <input type="number" class="quantity-input" name="quantity_<%= item.getItemId() %>" value="1" min="1" onchange="calculateTotal()" disabled>
                <input type="hidden" class="price" value="<%= item.getPrice() %>">
            </div>
            <%
                }
            } else {
            %>
            <p>No menu items found.</p>
            <%
                }
            %>
        </div>

        <h3>Total Price: $<span id="totalPrice">0.00</span></h3>

        <br>
        <button type="submit" class="button">Place Order & Reserve</button>
    </form>

    <br><br>
    <a href="index.jsp" class="button">Back to Home</a>
</div>


<script>
    function calculateTotal() {
        let total = 0;
        const items = document.querySelectorAll('.menu-item');

        items.forEach(item => {
            const checkbox = item.querySelector('.menu-check');
            const quantityInput = item.querySelector('.quantity-input');
            const price = parseFloat(item.querySelector('.price').value);

            if (checkbox.checked) {
                quantityInput.disabled = false;
                const quantity = parseInt(quantityInput.value) || 1;
                total += price * quantity;
            } else {
                quantityInput.disabled = true;
            }
        });

        document.getElementById('totalPrice').textContent = total.toFixed(2);
    }


    window.onload = function() {
        const now = new Date();
        now.setMinutes(now.getMinutes() + 30); // Default to 30 minutes from now
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');

        document.getElementById('reservationTime').value = `${year}-${month}-${day}T${hours}:${minutes}`;
    }
</script>

</body>
</html>

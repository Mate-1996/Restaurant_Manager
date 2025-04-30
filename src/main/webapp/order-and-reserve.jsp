<%@ page import="java.util.List" %>
<%@ page import="com.example.restaurant.entities.MenuItem" %>
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
    </style>
</head>
<body>

<div class="container">
    <h1>Order Your Meal & Reserve a Table</h1>

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
                    List<com.example.restaurant.entities.Table> tables = (List<com.example.restaurant.entities.Table>) request.getAttribute("tables");
                    if (tables != null) {
                        for (com.example.restaurant.entities.Table table : tables) {
                %>
                <option value="<%= table.getTableNumber() %>">
                    Table <%= table.getTableNumber() %> (Capacity: <%= table.getCapacity() %>, Status: <%= table.getStatus() %>)
                </option>
                <%
                        }
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
                List<com.example.restaurant.entities.MenuItem> menuItems = (List<com.example.restaurant.entities.MenuItem>) request.getAttribute("menuItems");
                if (menuItems != null && !menuItems.isEmpty()) {
                    for (com.example.restaurant.entities.MenuItem item : menuItems) {
            %>
            <div class="menu-item">
                <input type="checkbox" class="menu-check" name="menuItemIds" value="<%= item.getItemId() %>" onchange="calculateTotal()">
                <label>
                    <strong><%= item.getName() %></strong> - $<%= item.getPrice() %><br/>
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
</script>

</body>
</html>

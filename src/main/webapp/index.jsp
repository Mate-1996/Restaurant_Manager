<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome to Our Restaurant</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            text-align: center;
        }
        .container {
            margin-top: 50px;
        }
        h1 {
            font-size: 36px;
            color: #333;
        }
        p {
            font-size: 18px;
            color: #666;
        }
        .section {
            margin-top: 40px;
        }
        .button {
            display: inline-block;
            margin: 10px;
            padding: 15px 30px;
            font-size: 18px;
            color: white;
            background-color: #ff7043;
            border: none;
            border-radius: 8px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }
        .button:hover {
            background-color: #ff5722;
        }
    </style>
</head>
<body>

<div class="container">
    <h1> Welcome to Our Restaurant</h1>
    <p>Enjoy fresh meals, cozy atmosphere, and easy online services!</p>


    <div class="section">
        <h2>For Customers</h2>
        <a href="menu-servlet" class="button">View Menu</a>
        <a href="order-reservation-servlet" class="button">Order & Reserve Together</a>
        <a href="reservation.jsp" class="button">Reserve a table</a>

    </div>


    <div class="section">
        <h2>For Admins</h2>
        <a href="admin-menu-servlet" class="button">Manage Menu Items</a>
        <a href="admin-order-servlet" class="button">View All Orders</a>
        <a href="login-test.jsp" class="button">Staff Login</a>
        <a href="table-servlet" class="button">Manage Tables</a>
        <a href="create-user.jsp" class="button">Create Staff User</a>
    </div>
</div>

</body>
</html>



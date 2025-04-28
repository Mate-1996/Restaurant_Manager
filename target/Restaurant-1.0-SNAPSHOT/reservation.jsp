<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Make a Reservation</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Reserve a Table</h1>

<form action="reservation-servlet" method="post">
    <label for="customerName">Your Name:</label>
    <input type="text" id="customerName" name="customerName" required><br><br>

    <label for="tableNumber">Table Number:</label>
    <input type="number" id="tableNumber" name="tableNumber" required><br><br>

    <label for="reservationTime">Reservation Time:</label>
    <input type="datetime-local" id="reservationTime" name="reservationTime" required><br><br>

    <input type="submit" value="Reserve Table" class="button">
</form>

</body>
</html>



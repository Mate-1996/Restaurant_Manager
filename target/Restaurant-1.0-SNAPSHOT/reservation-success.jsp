<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reservation Success</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <h1>Reservation Successful!</h1>
    <p><%= request.getAttribute("message") %></p>

    <a href="index.jsp" class="button">Back to Home</a>
</div>

</body>
</html>


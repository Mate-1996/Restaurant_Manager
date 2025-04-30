<!DOCTYPE html>
<html>
<head>
    <title>Create New User</title>
    <script>
        function toggleFields() {
            var role = document.getElementById("role").value;
            document.getElementById("waiterFields").style.display = role === "Waiter" ? "block" : "none";
            document.getElementById("chefFields").style.display = role === "Chef" ? "block" : "none";
        }
    </script>
</head>
<body>
<h1>Create New User</h1>
<form action="create-user-servlet" method="post">
    Name: <input type="text" name="name" required><br><br>
    Role:
    <select name="role" id="role" onchange="toggleFields()" required>
        <option value="">Select...</option>
        <option value="Waiter">Waiter</option>
        <option value="Chef">Chef</option>
    </select><br><br>

    <div id="waiterFields" style="display:none;">
        Assign Tables (comma separated): <input type="text" name="tables"><br><br>
    </div>

    <div id="chefFields" style="display:none;">
        Kitchen Station: <input type="text" name="station"><br><br>
    </div>

    <button type="submit">Create User</button>
</form>
<br><a href="index.jsp">Back to Home</a>
</body>
</html>


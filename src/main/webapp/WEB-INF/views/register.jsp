<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <script>
        function validateForm() {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirm_password").value;
            if (password !== confirmPassword) {
                alert("Passwords do not match.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<main>
	<div class="container">
		<h1>New User Registration</h1>
		<p>Enter desired user information below</p>
		<form action="register" method="post" onsubmit="return validateForm()">
		    <label for="username">Username:</label>
		    <input type="text" id="username" name="username" required>
		    <br>
		    <label for="email">Email:</label>
		    <input type="email" id="email" name="email" required>
		    <br>
		    <label for="password">Password:</label>
		    <input type="password" id="password" name="password" required>
		    <br>
		    <label for="confirm_password">Retype Password:</label>
		    <input type="password" id="confirm_password" name="confirm_password" required>
		    <br>
		    <input type="submit" value="Login">
		</form>
		
		<% String errorMessage = (String) request.getAttribute("errorMessage");
		    if (errorMessage != null) { %>
		    <p style="color:red;"><%= errorMessage %></p>
		<% } %>
	</div>	
</main>
</body>
</html>
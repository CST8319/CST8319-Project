<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<main>
	<div class="container">
	<h1>Forgot Password</h1>
	<p>Enter your email to receive a password reset code:</p>
	
	<form action="forgotPassword" method="post">
	    <label for="email">Email:</label>
	    <input type="email" id="email" name="email" required>
	    <br>
	    <input type="submit" value="Send Reset Code">
	</form>
	
	<% String errorMessage = (String) request.getAttribute("errorMessage");
	    if (errorMessage != null) { %>
	    <p style="color:red;"><%= errorMessage %></p>
	<% } %>
	</div>
</main>
</body>
</html>

<!DOCTYPE html>
<html>
<head>
    <title>Reset Password</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<main>
	<div class="container">
		<h1>Reset Password</h1>
		<p>Please enter the reset code sent to your email: <%= request.getAttribute("email") %></p>
		
		<form action="resetPassword" method="post">
		    <input type="hidden" name="email" value="<%= request.getAttribute("email") %>">
		    <label for="reset_code">Reset Code:</label>
		    <input type="text" id="reset_code" name="reset_code" required>
		    <br>
		    <label for="new_password">New Password:</label>
		    <input type="password" id="new_password" name="new_password" required>
		    <br>
		    <input type="submit" value="Reset Password">
		</form>
		
		<form action="resendResetCode" method="post">
		    <input type="hidden" name="email" value="<%= request.getAttribute("email") %>">
		    <input type="submit" value="Resend Reset Code">
		</form>
		
		<% String message = (String) request.getAttribute("message");
		   if (message != null) { %>
		    <p style="color:green;"><%= message %></p>
		<% } %>
		<% String errorMessage = (String) request.getAttribute("errorMessage");
		   if (errorMessage != null) { %>
		    <p style="color:red;"><%= errorMessage %></p>
		<% } %>
	</div>
</main>
</body>
</html>

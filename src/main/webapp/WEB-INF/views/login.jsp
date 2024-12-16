<!DOCTYPE html>
<html>

<head>
	<title>Login</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>

	<main>
		<div class="container">
			<h1>Get Well Get Fit</h1>
			<p>Please login to access site content.</p>

			<form action="login" method="post">
				<label for="username_or_email">Username/Email:</label>
				<input type="text" id="username_or_email" name="username_or_email" required>
				<br>
				<label for="password">Password:</label>
				<input type="password" id="password" name="password" required>
				<br>
				<input type="submit" value="Enter Site">
				<input type="button" value="Forgot Password"
					onclick="location.href='${pageContext.request.contextPath}/controller/forgotPassword'">
			</form>

			<p>No account?</p>
			<input type="button" value="Register Here"
				onclick="location.href='${pageContext.request.contextPath}/controller/register'">

			<% String errorMessage=(String) request.getAttribute("errorMessage"); if (errorMessage !=null) { %>
				<p style="color:red;">
					<%= errorMessage %>
				</p>
				<% } %>
		</div>
	</main>
</body>

</html>
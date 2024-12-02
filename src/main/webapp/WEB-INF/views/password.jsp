<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Password Reset</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <form action="passwordReset" method="post">
        <input type="email" name="email" placeholder="Enter your email" required>
        <button type="submit">Reset Password</button>
    </form>


    <form action="resetPassword" method="post">
        <input type="text" name="token" placeholder="Enter your token" required>
        <input type="password" name="newPassword" placeholder="Enter new password" required>
        <button type="submit">Submit</button>
    </form>
</body>
</html>
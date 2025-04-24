

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reset Password</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link rel="stylesheet" type="text/css" href="header.css">
    <link rel="stylesheet" type="text/css" href="dashboard.css">
    </head>
    <body>
    <h2>Reset Password</h2>
    <form action="ResetPassword" method="post">
        <label for="regno">Registration Number:</label><br>
        <input type="text" name="regno" required><br><br>

        <label for="new_password">New Password:</label><br>
        <input type="password" name="new_password" required><br><br>

        <label for="confirm_password">Confirm Password:</label><br>
        <input type="password" name="confirm_password" required><br><br>

        <button type="submit">Reset Password</button>
    </form>
    </body>
</html>

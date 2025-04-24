<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Student Login</title>
        <link rel="stylesheet" href="home.css" />
        <link rel="stylesheet" href="header.css" />
        <link rel="stylesheet" href="styles.css" />
        <script>
            // Function to validate the form input
            function validateForm() {
                let regno = document.getElementById("regno").value;
                let password = document.getElementById("password").value;
                let regnoPattern = /^[A-Z]\d{2}\/\d{5}\/\d{2}$/; // Format: S13/09399/29

                // Validate registration number format
                if (!regnoPattern.test(regno)) {
                    alert("Enter a valid registration number (Format: S13/09399/29)");
                    return false;
                }

                // Ensure the password is not empty
                if (password.trim() === "") {
                    alert("Password cannot be empty");
                    return false;
                }
                return true;
            }

            // Function to show the error alert
            function showError(message) {
                alert(message); // Display the error message passed
            }

            // On page load, check for the error parameter and display an alert
            window.onload = function() {
                var urlParams = new URLSearchParams(window.location.search);
                var error = urlParams.get('error');
                if (error === '1') {
                    showError("Wrong Credentials, try again");
                }
            }
        </script>
    </head>
    <body>
        <header>
            <%@include file="header.jsp" %>
        </header>

        <h2>Student Login</h2>

        <!-- Login form with validation on submit -->
        <form action="loginServlet" method="post" onsubmit="return validateForm()">
            <label for="regno">Registration Number:</label>
            <input type="text" id="regno" name="regno" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>

            <input type="submit" value="Login"> <br> <br>
                    <a href="resetPassword.jsp" style = "text-decoration:none; color:#00A651";>Forgot Password?</a>
        </form>
        <br>

        <footer class="footer">
            <%@include file="footer.jsp" %>
        </footer>
        </body>
</html>

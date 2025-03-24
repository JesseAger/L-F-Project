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
            function validateForm() {
                let regno = document.getElementById("regno").value;
                let password = document.getElementById("password").value;
                let regnoPattern = /^[A-Z]\d{2}\/\d{5}\/\d{2}$/; // Format S13/09399/29

                if (!regnoPattern.test(regno)) {
                    alert("Enter a valid registration number (Format: S13/09399/29)");
                    return false;
                }

                if (password.trim() === "") {
                    alert("Password cannot be empty");
                    return false;
                }
                return true;
            }

            function showError() {
                alert("Wrong Credentials, try again");
            }
        </script>
    </head>
    <body>
        <header>
            <%@include file="header.jsp" %>
            
        </header>
        <h2>Student Login</h2>
        <form action="loginServlet" method="post" onsubmit="return validateForm()">
            <label for="regno">Registration Number:</label>
            <input type="text" id="regno" name="regno" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>

            <input type="submit" value="Login">
        </form>

        <% 
            String error = request.getParameter("error"); 
            if (error != null && error.equals("1")) { 
        %>
            <script> showError(); </script>
        <% } %>
        
        <footer class="footer">
            <%@include file="footer.jsp" %>
            </footer>
    </body>

</html>

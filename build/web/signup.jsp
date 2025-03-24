<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Sign Up</title>
                    <link rel="stylesheet" href="styles.css" />
                    <link rel="stylesheet" href="header.css" />
                     <link rel="stylesheet" href="home.css" />
                    
        <style>
            .error {
                color: red;
                font-size: 14px;
            }
        </style>
        <script>
            function validateForm() {
                let firstName = document.getElementById("firstname").value;
                let lastName = document.getElementById("lastname").value;
                let regNo = document.getElementById("regno").value;
                let phone = document.getElementById("contact").value;
                let password = document.getElementById("password").value;
                let confirmPassword = document.getElementById("confirm_password").value;

                let namePattern = /^[A-Za-z]+$/; // Only letters
                let regNoPattern = /^[A-Z]\d{2}\/\d{5}\/\d{2}$/; // S13/09399/29 format
                let phonePattern = /^\d{10}$/; // Exactly 10 digits
                let passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/; // Strong password

                let valid = true;

                // First Name Validation
                if (!namePattern.test(firstName)) {
                    document.getElementById("firstnameError").innerHTML = "Name must contain only alphabetical characters, A-Z.";
                    valid = false;
                } else {
                    document.getElementById("firstnameError").innerHTML = "";
                }

                // Last Name Validation
                if (!namePattern.test(lastName)) {
                    document.getElementById("lastnameError").innerHTML = "Name must contain only alphabetical characters, A-Z.";
                    valid = false;
                } else {
                    document.getElementById("lastnameError").innerHTML = "";
                }

                // Registration Number Validation
                if (!regNoPattern.test(regNo)) {
                    document.getElementById("regnoError").innerHTML = "Enter a valid registration number (e.g., S13/09399/29).";
                    valid = false;
                } else {
                    document.getElementById("regnoError").innerHTML = "";
                }

                // Phone Number Validation
                if (!phonePattern.test(phone)) {
                    document.getElementById("contactError").innerHTML = "Enter a valid phone number (exactly 10 digits).";
                    valid = false;
                } else {
                    document.getElementById("contactError").innerHTML = "";
                }

                // Password Validation
                if (!passwordPattern.test(password)) {
                    document.getElementById("passwordError").innerHTML = "Password must be at least 8 characters with at least one number, lowercase, uppercase, and a special character.";
                    valid = false;
                } else {
                    document.getElementById("passwordError").innerHTML = "";
                }

                // Confirm Password Validation
                if (password !== confirmPassword) {
                    document.getElementById("confirmPasswordError").innerHTML = "Passwords do not match.";
                    valid = false;
                } else {
                    document.getElementById("confirmPasswordError").innerHTML = "";
                }

                return valid; // If any validation fails, prevent form submission
            }
        </script>
    </head>
    <body>
        <header>
        <%@include file="header.jsp" %>
        </header>
        
        <form action="signupServlet" method="post" onsubmit="return validateForm()">
            
            <label for="firstname">First Name:</label>
            <input type="text" id="firstname" name="firstname" required>
            <div id="firstnameError" class="error"></div>
            <br>
            
            <label for="lastname">Last Name:</label>
            <input type="text" id="lastname" name="lastname" required>
            <div id="lastnameError" class="error"></div>
            <br>
            
            <label for="regno">Registration Number:</label>
            <input type="text" id="regno" name="regno" required>
            <div id="regnoError" class="error"></div>
            <br>
            
            <label for="contact">Phone Number:</label>
            <input type="text" id="contact" name="contact" required>
            <div id="contactError" class="error"></div>
            <br>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <div id="passwordError" class="error"></div>
            <br>

            <label for="confirm_password">Repeat Password:</label>
            <input type="password" id="confirm_password" name="confirm_password" required>
            <div id="confirmPasswordError" class="error"></div>
            <br>

            <input type="submit" value="Sign Up">
            
        </form>
        
        <footer>
        <%@include file="footer.jsp" %>
        </footer>
    </body>
</html>

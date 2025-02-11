<%-- 
    Document   : report.jsp
    Created on : Apr 2, 2024, 6:54:33 AM
    Author     : FABIUS
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Report Lost Item</title>
    <link rel="stylesheet" href="styles.css">
    <script>
   
function validateForm() {
    var x = document.forms["myform"]["name"].value;
    var y = document.forms["myform"]["email"].value;
    var t = document.forms["myform"]["contact_number"].value;
    var w = document.forms["myform"]["location_lost"].value;
    var r = document.forms["myform"]["item_lost"].value;
    var z = document.forms["myform"]["date_lost"].value;

    // Regular expressions for validation
    var nameRegex = /^[a-zA-Z ]+$/;
    var contactRegex = /^\d{10}$/; // 10 digits only
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Basic email validation
    var dateRegex = /^\d{4}-\d{2}-\d{2}$/; // YYYY-MM-DD format

    if (!x.trim()) {
        alert("Please enter name.");
        document.getElementById("name").focus();
        return false;
    }
    
    if (!x.match(nameRegex)) {
    alert("Please enter a valid name (only letters and spaces allowed).");
    document.getElementById("name").focus();
    return false;
    }

    if (!t.trim()) {
        alert("Please enter contact number.");
        document.getElementById("contactNumber").focus();
        return false;
    }
    
    if (!t.match(contactRegex)) {
    alert("Please enter a valid contact number (10 digits only).");
    document.getElementById("contactNumber").focus();
    return false;
    }
    
    if (!y.trim()) {
    alert("Please enter email.");
    document.getElementById("email").focus();
    return false;
    }
    
    if (!y.match(emailRegex)) {
    alert("Please enter a valid email address.");
    document.getElementById("email").focus();
    return false;
    }

    if (!w.trim()) {
        alert("Please enter location lost.");
        document.getElementById("locationLost").focus();
        return false;
    }
    
    if (!w.match(nameRegex)) {
    alert("Please enter a valid location (only letters and spaces allowed).");
    document.getElementById("locationLost").focus();
    return false;
    }


    if (!r.trim()) {
        alert("Please enter item lost.");
        document.getElementById("itemLost").focus();
        return false;
    }

    if (!z.trim()) {
        alert("Please enter date lost.");
        document.getElementById("dateLost").focus();
        return false;
    }


    if (!z.match(dateRegex)) {
        alert("Please enter a valid date (YYYY-MM-DD format).");
        document.getElementById("dateLost").focus();
        return false;
    }

    return true; // Form submission allowed if all validations pass
}

    </script>
</head>
<body>
    <header>
        <div class="header-container">
            <a href="homepage.jsp">
                <img src="images/logo.jpeg" alt="Logo" class="logo-img">
            </a>
    
            <h1>Egerton University <br>Report Entry</h1>
            <nav class="nav-links">
                <ul>
                  <li><a href="homepage.jsp">Home</a></li>
                  <li><a href="admin-login.jsp">Staff Login</a></li>
                  <li><a href="report.jsp">Report Lost Item</a> </li>
                  <li><a href="records.jsp">Records Entry</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <form name="myform" action="ReporterServlet" method="post"  onsubmit="return validateForm()">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" ><br>

            <label for="contact_number">Contact Number:</label>
            <input type="text" id="contactNumber" name="contact_number" ><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" ><br>

            <label for="location_lost">Location Lost:</label>
            <input type="text" id="locationLost" name="location_lost" ><br>

            <label for="item_lost">Item Lost:</label>
            <input type="text" id="itemLost" name="item_lost" ><br>

            <label for="date_lost">Date Lost:</label>
            <input type="date" id="dateLost" name="date_lost" ><br>

            <input type="submit" value="Submit">
            
        </form>

    </main>
    <footer class="footer" id="footer">
        <%@ include file="footer.jsp" %>
    </footer>
</body>
</html>
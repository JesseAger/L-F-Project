<%-- 
    Document   : error_java.jsp
    Created on : Apr 1, 2024, 10:33:10 AM
    Author     : FABIUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 Error</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <header>
        <div class="header-container">
            <a href="homepage.jsp">
                <img src="images/logo.jpeg" alt="Logo" class="logo-img">
              </a>

            <h1>Egerton University <br>Lost and Found System</h1>
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
    <div class="center">
        <h1> 404 error </h1>
        <p>The server was not able to find the file you requested</p>
        <p>To continue, click the Back button.</p>
    </div>

    <footer class="footer" id="footer">
      <%@ include file="footer.jsp" %>
    </footer>
</body>
</html>
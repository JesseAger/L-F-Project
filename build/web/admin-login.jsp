<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <div class="header-container">
            <a href="homepage.jsp">
                <img src="images/logo.jpeg" alt="Logo" class="logo-img">
              </a>
    
            <h1>Egerton University <br> Staff Login</h1>
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
    <main class="main-content">
        <form action="AdminServlet"  method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>

            <input type="submit" value="Login">
        </form>
    </main>
</body>
    <footer class="footer" id="footer">
      <%@ include file="footer.jsp" %>
    </footer>
</html>
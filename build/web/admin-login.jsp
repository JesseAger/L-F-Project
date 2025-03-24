<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%
    Boolean staffLoggedIn = (Boolean) session.getAttribute("staffLoggedIn");
    if (staffLoggedIn == null) {
        staffLoggedIn = false;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <%@ include file="header.jsp" %>
    </header>

    <main class="main-content">
                            <% if (staffLoggedIn) { %>
                        <li><a href="records.jsp">Records Entry</a></li> <!-- Visible only to staff -->
                    <% } %>
        <form action="AdminServlet" method="post">
             <h3>Egerton University <br> Staff Login</h3>
            <label for="username">Staff Number:</label>
            <input type="text" id="username" name="username" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>

            <input type="submit" value="Login">
        </form>
    </main>

    <footer class="footer" id="footer">
        <%@ include file="footer.jsp" %>
    </footer>
</body>
</html>

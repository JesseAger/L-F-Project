<%@ page import="business.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link rel="stylesheet" type="text/css" href="header.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .header {
            background: #007BFF;
            color: white;
            padding: 15px;
            text-align: center;
            font-size: 20px;
            position: relative;
        }
        .menu-icon {
            position: absolute;
            left: 20px;
            top: 15px;
            font-size: 24px;
            cursor: pointer;
            display: block;
        }
        .sidebar {
            position: fixed;
            left: -250px;
            width: 250px;
            height: 100%;
            background: #222;
            padding-top: 20px;
            transition: 0.3s;
        }
        .sidebar a {
            display: block;
            padding: 15px;
            color: white;
            text-decoration: none;
            font-size: 18px;
            text-align: center;
        }
        .sidebar a:hover {
            background: #007BFF;
        }
        .main-content {
            margin-left: 0;
            padding: 20px;
            text-align: center;
            transition: 0.3s;
        }
        .show-sidebar {
            left: 0;
        }
        .full-screen {
            width: 100%;
            height: 100vh;
            background: white;
            padding: 20px;
            overflow: auto;
        }
    </style>
</head>
<body>
    <header>
        <%@include file="header.jsp" %>
    </header>

    <%
        String username = "User"; // Default name
        if (session != null && session.getAttribute("student") != null) {
            User student = (User) session.getAttribute("student");
            username = student.getUsername(); 
        }
    %>

    <div class="header">
        Student Dashboard
        <span class="menu-icon" id="menu-icon">☰</span>
    </div>

    <!-- Sidebar -->
    <div id="sidebar" class="sidebar">
        <a href="#" id="lostItems">Lost Items</a>
        <a href="#" id="foundItems">Found Items</a>
        <a href="#" id="reportLost">Report Lost Item</a>
        <a href="<%= request.getContextPath() %>/logout.jsp">Log Out</a>
    </div>

    <!-- Main Content -->
    <div id="main-content" class="main-content">
        <h2>Explore your Dashboard</h2>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            function toggleSidebar() {
                let sidebar = document.getElementById("sidebar");
                sidebar.classList.toggle("show-sidebar");
            }

            function loadPage(page) {
                let content = document.getElementById("main-content");
                document.getElementById("sidebar").classList.remove("show-sidebar"); // Hide sidebar

                if (page === "lostItems") {
                    content.innerHTML = "<div class='full-screen'><h2>Lost Items</h2><p>List of lost items here...</p></div>";
                } else if (page === "foundItems") {
                    content.innerHTML = "<div class='full-screen'><h2>Found Items</h2><p>List of found items here...</p></div>";
                } else if (page === "reportLost") {
                    content.innerHTML = `
                        <div class='full-screen'>
                            <h2>Report Lost Item</h2>
                            <form name="myform" action="ReporterServlet" method="post">
                                <label for="name">Name:</label>
                                <input type="text" id="name" name="name"><br>

                                <label for="contact_number">Contact Number:</label>
                                <input type="text" id="contactNumber" name="contact_number"><br>

                                <label for="email">Email:</label>
                                <input type="email" id="email" name="email"><br>

                                <label for="location_lost">Location Lost:</label>
                                <input type="text" id="locationLost" name="location_lost"><br>

                                <label for="item_lost">Item Lost:</label>
                                <input type="text" id="itemLost" name="item_lost"><br>

                                <label for="date_lost">Date Lost:</label>
                                <input type="date" id="dateLost" name="date_lost"><br>

                                <input type="submit" value="Submit">
                            </form>
                        </div>
                    `;
                }
            }

            // Event Listeners
            document.getElementById("menu-icon").addEventListener("click", toggleSidebar);
            document.getElementById("lostItems").addEventListener("click", function () {
                loadPage("lostItems");
            });
            document.getElementById("foundItems").addEventListener("click", function () {
                loadPage("foundItems");
            });
            document.getElementById("reportLost").addEventListener("click", function () {
                loadPage("reportLost");
            });
        });
    </script>

</body>
<footer class="footer">
    <%@include file="footer.jsp" %>
</footer>
</html>

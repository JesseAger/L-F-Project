<%@ page import="business.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Dashboard</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link rel="stylesheet" type="text/css" href="header.css">
    <link rel="stylesheet" type="text/css" href="dashboard.css">
</head>
<body>
    <header>
        <%@include file="header.jsp" %>
    </header>

    <div class="header">
        Staff Dashboard
        <span class="menu-icon" id="menu-icon">☰</span>
    </div>

    <div id="sidebar" class="sidebar">
        <a href="#" id="lostItems">Lost Items</a>
        <a href="#" id="foundItems">Found Items</a>
        <a href="#" id="postFound">Post Found Items</a>
    <a href="<c:url value='/LogOutServlet' />" class="logout-button">Logout</a>

    </div>

    <div id="main-content" class="main-content">
        <h2>Explore your Dashboard</h2>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            function toggleSidebar() {
                let sidebar = document.getElementById("sidebar");
                sidebar.classList.toggle("show-sidebar");
            }

            function setCookie(name, value, days) {
                let date = new Date();
                date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
                let expires = "expires=" + date.toUTCString();
                document.cookie = name + "=" + value + ";" + expires + ";path=/";
            }

            function getCookie(name) {
                let decodedCookie = decodeURIComponent(document.cookie);
                let cookies = decodedCookie.split(';');
                for (let i = 0; i < cookies.length; i++) {
                    let c = cookies[i].trim();
                    if (c.indexOf(name + "=") === 0) {
                        return c.substring(name.length + 1);
                    }
                }
                return "";
            }

            function loadPage(page) {
                let content = document.getElementById("main-content");
                document.getElementById("sidebar").classList.remove("show-sidebar");
                setCookie("lastPage", page, 1);

                if (page === "lostItems") {
                    content.innerHTML = "<div class='full-screen'><h2>Lost Items</h2><p>List of lost items here...</p><div id='lostItemContainer' class='cards-container'></div></div>";
           
                        fetch("lostItemServlet")
                        .then(response => response.json())
                        .then(items => {
                             console.log(items);
                            let container = document.getElementById("lostItemContainer");
                            container.innerHTML = "";

                            items.forEach(item => {
                                let card = document.createElement("div");
                                card.classList.add("card");

                                let imageUrl = item.image;
                                console.log("Image URL:", imageUrl);  // Debugging: Log image URL

                                card.innerHTML = 
                                  '<img src="' + imageUrl + '" alt="Item Image" onerror="this.onerror=null; this.src=\'default.jpg\';" />' +
                                  '<div class="card-info">' +
                                  '<h3>' + item.item_lost + '</h3>' +
                                  '<p>' + item.description + '</p>' +
                                  '</div>';

                                container.appendChild(card);
                            });
                        })
                        .catch(error => console.error("Error fetching lost items:", error));
                } else if (page === "foundItems") {
                    let content = document.getElementById("main-content");
                    content.innerHTML = "<div class='full-screen'><h2>Found Items</h2><div id='foundItemsContainer' class='cards-container'></div></div>";
                        
                } else if (page === "postFound") {
                    content.innerHTML = `
                        <div class='full-screen'>
                            <h3>Log Found Items</h3>
                            <form action="RecordsServlet" method="post" enctype="multipart/form-data">
                                <label for="item_name">Item Name:</label>
                                <input type="text" id="itemName" name="item_name" required /><br />
                                <label for="description">Description:</label>
                                <textarea id="description" name="description" rows="4" cols="50" required></textarea><br />
                                <label for="location_found">Location Found:</label>
                                <input type="text" id="locationFound" name="location_found" required /><br />
                                <label for="date_found">Date Found:</label>
                                <input type="date" id="date_found" name="date_found" required /><br />
                                <label for="image">Upload Image:</label>
                                <input type="file" id="image" name="image" accept="image/*" /><br />
                                <div style="margin-top: 20px;"></div>
                                <input type="submit" value="Submit" />
                            </form>
                            <br> <br>
                            <a href="DownloadServlet" class="download-button">Download Report</a><br>
                        </div>
                    `;
                }
            }

            document.getElementById("menu-icon").addEventListener("click", toggleSidebar);
            document.getElementById("lostItems").addEventListener("click", function () {
                loadPage("lostItems");
            });
            document.getElementById("foundItems").addEventListener("click", function () {
                loadPage("foundItems");
            });
            document.getElementById("postFound").addEventListener("click", function () {
                loadPage("postFound");
            });

            let lastPage = getCookie("lastPage");
            if (lastPage) {
                loadPage(lastPage);
            }
        });
    </script>

</body>
<footer class="footer">
    <%@include file="footer.jsp" %>
</footer>
</html>

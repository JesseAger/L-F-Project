<%@ page import="business.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link rel="stylesheet" type="text/css" href="header.css">
    <link rel="stylesheet" type="text/css" href="dashboard.css">

</head>
<body>
    <header>
        <%@include file="header.jsp" %>
    </header>

 <%
    String username = (String) session.getAttribute("student_name");

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
        <a href="#" id ="getClaims">Claims</a>
 <a href="<c:url value='/logoutServlet' />" class="logout-button">Logout</a>
    </div>

    <!-- Main Content -->
    <div id="main-content" class="main-content">
        <h2>Explore your Dashboard, <%= username %></h2>
    </div>

    <!-- Cookie Consent Banner -->
    <div id="cookie-banner">
        This website uses cookies to improve your experience. By clicking "Accept", you consent to the use of cookies.
        <button onclick="acceptCookies()">Accept</button>
    </div>

    <script>
       
        
        
// Cookie Consent Logic
function getCookie(name) {
    let cookies = document.cookie.split("; ");
    for (let i = 0; i < cookies.length; i++) {
        let [cookieName, cookieValue] = cookies[i].split("=");
        if (cookieName === name) {
            return cookieValue;
        }
    }
    return null;
}

function acceptCookies() {
    document.cookie = "cookiesAccepted=true; path=/; max-age=" + (60 * 60 * 24 * 30); // 30 days
    document.getElementById("cookie-banner").style.display = "none";
}



document.addEventListener("DOMContentLoaded", function () {
    // Show the cookie banner if cookies have not been accepted
    if (!getCookie("cookiesAccepted")) {
        document.getElementById("cookie-banner").style.display = "block";
    }

    function toggleSidebar() {
        let sidebar = document.getElementById("sidebar");
        sidebar.classList.toggle("show-sidebar");
    }
      

    function loadPage(page) {
        let content = document.getElementById("main-content");
        document.getElementById("sidebar").classList.remove("show-sidebar"); // Hide sidebar

        if (page === "foundItems") {
            content.innerHTML = `
                <div class='full-screen'>
                    <h2>Found Items</h2>
                    <div id='foundItemsContainer' class='cards-container'></div>
                </div>
            `;

        fetch("foundItemsServlet")
            .then(response => response.json())
            .then(items => {
                 console.log(items);
                let container = document.getElementById("foundItemsContainer");
                container.innerHTML = "";

                items.forEach(item => {
                    let card = document.createElement("div");
                    card.classList.add("card");
                    
                    let imageUrl = item.image;
                    console.log("Image URL:", imageUrl);  // Debugging: Log image URL
                    
                    
                   
                    card.innerHTML = `
                       <img src="`+imageUrl+`"
                        alt="Item Image" 
                        onerror="this.onerror=null; this.src='default.jpg';" /> 
                        <div class="card-info">
                       <form action="claimeditems" method="post" onsubmit="passPValue()">
                            <h3>`+item.itemName+`</h3>
                            <p>`+item.description+`</p>
                            <p id= "pTag" > `+item.item_no+` </p>
                    
                            <input type="hidden" id="hiddenInput" name="pValue" value=`+item.item_no+`>
                           <button type="submit" id="myButton">Claim</button>
                         </form>
                        </div> `
                    ;
                    container.appendChild(card);
                    
                   
                });
            })
            .catch(error => console.error("Error fetching found items:", error));

        } else if (page === "lostItems") {
            content.innerHTML = "<div class='full-screen'><h2>Lost Items</h2><p>Lost items here...</p></div>";
        } else if (page === "reportLost") {
            content.innerHTML = `
                <div class='full-screen'>
                    <h2>Report Lost Item</h2>
                    <form action="ReporterServlet" method="post" enctype="multipart/form-data">
                          <label for="reg_no">Reg No:</label>
                          <input type="text" id="regNo" name="reg_no"><br>

                          <label for="phone_number">Phone Number:</label>
                          <input type="text" id="phoneNumber" name="phone_number"><br>

                          <label for="email">Email:</label>
                          <input type="email" id="email" name="email"><br>

                          <label for="location_lost">Location Lost:</label>
                          <input type="text" id="locationLost" name="location_lost"><br>

                          <label for="item_lost">Item Lost:</label>
                          <input type="text" id="itemLost" name="item_lost"><br>

                          <label for="description">Description:</label>
                          <input type="text" id="Description" name="description"><br>

                          <label for="date_lost">Date Lost:</label>
                          <input type="date" id="dateLost" name="date_lost"><br>
                
                        <label for="image">Upload Image:</label>
                        <input type="file" id="image" name="image" accept="image/*" /><br />
                        <div style="margin-top: 20px;"></div>

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

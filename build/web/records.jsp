<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% if(session.getAttribute("user") == null) { %>
    <script>
        alert("Please login before accessing the records page.");
        window.location.href = "admin-login.jsp";
    </script>
<% } else { %>
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>Staff Records Entry</title>
            <link rel="stylesheet" href="styles.css" />
        </head>
        <body>
            <header>
                <%@ include file="header.jsp" %>
                  <a href="LogOutServlet">Logout</a>
            </header>
            <main class="main-content">
                <form action="RecordsServlet" method="post" enctype="multipart/form-data">
                    <label for="item_name">Item Name:</label>
                    <input type="text" id="itemName" name="item_name" required /><br />
                    <label for="description">Description:</label>
                    <textarea
                        id="description"
                        name="description"
                        rows="4"
                        cols="50"
                        required
                    ></textarea
                    ><br />

                    <label for="location_found">Location Found:</label>
                    <input
                        type="text"
                        id="locationFound"
                        name="location_found"
                        required
                    /><br />

                    <label for="date_found">Date Found:</label>
                    <input type="date" id="date_found" name="date_found" required /><br />
                    
                    <label for="image">Upload Image:</label>
                    <input type="file" id="image" name="image" accept="image/*" /><br />
                    <div style="margin-top: 20px;"></div>

                    <input type="submit" value="Submit" />
                </form>

                <!-- Add the download button -->
                <a href="DownloadServlet" class="download-button">Download Report</a><br>
            </main><br><br>
            <footer class="footer" id="footer">
                <%@ include file="footer.jsp" %>
            </footer>
        </body>
    </html>
<% } %>

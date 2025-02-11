<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Lost and Found System</title>
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
    
    <main class="main-content">
        <h1>Lost and Found Items</h1>

        <div class="container">
        <select id="category" onchange="showItems()" style="width: auto;">
          <option value="" disabled selected>Please select a category</option> 
          <option value="electronics">Electronics</option>
          <option value="accessories">Accessories</option>
          <option value="clothing">Clothing</option>
          <option value="bags_luggage">Bags and Luggage</option>
          <option value="keys_personal">Keys and Personal Items</option>
        </select>
        </div>

        <div class="img-container">
            <div class="image-container selected-category" id="electronicsCategory">
                <div class="image-category">
                    <h2> Electronics</h2>
                    <img src="images/personal4.jpg" alt="Phone">
                    <img src="images/phone1.jpg" alt="Laptop">
                    <img src="images/image17.jpg" alt="Earbuds">
                    <img src="images/phone5.jpg" alt="Laptop">
                    <img src="images/phone2_1.jpeg" alt="Laptop">
                    <img src="images/laptop2.jpeg" alt="Laptop">
                </div>
            </div>
            <div class="image-container selected-category" id="accessoriesCategory">
                <div class="image-category">
                    <h2> Accessories</h2>
                    <img src="images/anklet.jpg" alt="Phone">
                    <img src="images/bracelet.webp" alt="Laptop">
                    <img src="images/necklace.webp" alt="Earbuds">
                    <img src="images/ring.webp" alt="Laptop">
                    <img src="images/waistbead.webp" alt="Laptop">
                    <img src="images/anklet.jpg" alt="Laptop">
                </div>
            </div>
            <div class="image-container selected-category" id="clothingCategory">
                <div class="image-category">
                    <h2> Clothing </h2>
                    <img src="images/sweater.jpg" alt="Phone">
                    <img src="images/puffer.png" alt="Laptop">
                    <img src="images/cap.png" alt="Earbuds">
                    <img src="images/half.png" alt="Laptop">
                    <img src="images/hoodie1.png" alt="Laptop">
                    <img src="images/hoodie2.png" alt="Laptop">
                </div>
            </div>
            <div class="image-container selected-category" id="bags_luggageCategory">
                <div class="image-category">
                    <h2> Bags and Luggage </h2>
                    <img src="images/bag.jpg" alt="bag">
                    <img src="images/bag4.jpg" alt="bag">
                    <img src="images/bag2.jpg" alt="bag">
                    <img src="images/bag3.jpg" alt="bag">
                    <img src="images/bag4.jpg" alt="bag">
                    <img src="images/bag5.jpg" alt="bag">
                </div>
            </div>
            <div class="image-container selected-category" id="keys_personalCategory">
                <div class="image-category">
                    <h2> Keys and Personal Items </h2>
                    <img src="images/personal3.jpg" alt="Personal Item">
                    <img src="images/image27.jpg" alt="personal item">
                    <img src="images/personal2.jpg" alt="personal item">
                    <img src="images/id2.jpeg" alt="id">
                    <img src="images/image20.jpg" alt="personal item">
                    <img src="images/image18.jpg" alt="id">
                </div>
            </div>
        
        </div><br>
    </main>


     <script>
        function showItems() {
            var select = document.getElementById("category");
            var selectedCategory = select.options[select.selectedIndex].value;
            var categories = document.getElementsByClassName("image-container");

            for (var i = 0; i < categories.length; i++) {
                categories[i].style.display = "none";
            }

            document.getElementById(selectedCategory + "Category").style.display = "block";
        }
    </script>

    
    <footer class="footer" id="footer">
         <%@ include file="footer.jsp" %>
         
    </footer>


</body>
</html>

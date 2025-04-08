<form name="myform" action="ReporterServlet" method="post" onsubmit="return validateForm()">
    <label for="reg_no">Reg No:</label>
    <input type="text" id="reg_no" name="reg_no"><br>

    <label for="phone_number">Phone Number:</label>
    <input type="text" id="phoneNumber" name="phone_number"><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email"><br>

    <label for="location_lost">Location Lost:</label>
    <input type="text" id="locationLost" name="location_lost"><br>

    <label for="item_lost">Item Lost:</label>
    <input type="text" id="itemLost" name="item_lost"><br>
    
    <label for="description">Description:</label>
    <input type="text" id="description" name="description"><br>

    <label for="date_lost">Date Lost:</label>
    <input type="date" id="dateLost" name="date_lost"><br>

    <input type="submit" value="Submit">
</form>

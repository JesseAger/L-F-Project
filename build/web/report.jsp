<form name="myform" action="ReporterServlet" method="post" onsubmit="return validateForm()">
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
    
    <label for="image">Upload Image:</label>
    <input type="file" id="image" name="image" accept="image/*" /><br />
    
    <label for="image">Upload Image:</label>
    <input type="file" id="image" name="image" accept="image/*" /><br />
    <div style="margin-top: 20px;"></div>

    <label for="date_lost">Date Lost:</label>
    <input type="date" id="dateLost" name="date_lost"><br>

    <input type="submit" value="Submit">
</form>

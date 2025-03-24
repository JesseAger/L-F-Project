<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Lost and Found System</title>
  <link rel="stylesheet" type="text/css" href="styles.css">
    <link rel="stylesheet" type="text/css" href="home.css">

</head>


<body>
    <header>
        <%@ include file='header.jsp'%>
    </header>
    
    <div class="admin-div">
        
        <p>Welcome to Egerton University Lost and Found System,
        Institution-based web application that creates seamless retrieval of 
        found items and announcement(Posting) of lost items. Security department 
        plays administrative role in managing the system and the items.
        
        Login as a staff using the credentials provided
        <br>
        <a href="admin-login.jsp">Staff Login</a>
        </p>
        
    </div>
    
    <div class="student-div">
        <p>
            As student who embraces application of technology in automation and 
            making work easier, create an account to have access to a system the will make your
            life easier.
            <br>
            <a href="signup.jsp">Student Sign up</a>
            <br>
            Already have an account?
            <br>
            <a href="login.jsp">Student Login</a>
        </p>
    </div>
    
    <footer class="footer" id="footer">
         <%@ include file="footer.jsp" %>
         
    </footer>


</body>
</html>


import Response.ResponseBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utils.SessionCheck;
import business.User;
import Utils.ServletUtils;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            try {
                PrintWriter out = ServletUtils.getPrintWriter(response);
                Connection conn = ServletUtils.getDBConnection();
                
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                
               
                String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, password);
                    ResultSet rs = statement.executeQuery();
                    
                    if (rs.next()) {
                        User user = new User(username, password);
                        // Authentication successful, redirect to records.html
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                                              
                        Cookie ck=new Cookie("user",username);  
                        response.addCookie(ck);
                        if (SessionCheck.isUserLoggedIn(request)) {
                            // User is logged in, continue with the logic
                            ResponseBuilder.buildRedirectResponse(out, "Successful login, welcome " + username + "!", "adminDashboard.jsp");
                        } 
                        else {
                            // User is not logged in, redirect to login page
                            ResponseBuilder.buildRedirectResponse(out, "You are not logged in. Please log in to continue.", "admin-login.jsp");
                        }
                        
                       
 
                    } else {
                        // Authentication failed, display error message
                        ResponseBuilder.buildRedirectResponse(out, "Authentication failed. Please check your username and password.", "admin-login.jsp");
                    }
                }
            } catch (SQLException ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    }



package student;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utils.ServletUtils;
import business.User;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/signupServlet")
public class signupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get database connection
            Connection conn = ServletUtils.getDBConnection();
            
            // Retrieve student signup parameters from the form
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            String regNo = request.getParameter("regno");
            String contact = request.getParameter("contact");
            String password = request.getParameter("password");
            
            // Hash the password using BCrypt (automatically handles salt generation)
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            
            // Insert the new student record into the database
            String sql = "INSERT INTO student (firstname, lastname, regno, contact, password) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, regNo);
                statement.setString(4, contact);
                statement.setString(5, hashedPassword);
                
                int rows = statement.executeUpdate();
                HttpSession session = request.getSession();
                
                if (rows > 0) {
                    // Signup successful, set session attribute "student"
                    User student = new User(regNo, hashedPassword); // You may store regNo as username
                    session.setAttribute("student", student);
                    
                    // Optionally, set a cookie for the student
                    Cookie ck = new Cookie("student", regNo);
                    response.addCookie(ck);
                    
                    // Optionally, store a success message in the session
                    session.setAttribute("message", "Signup successful, please login!");
                    
                    // Redirect to login.jsp
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                } else {
                    // Insertion failed; store error message and redirect back to signup.jsp
                    session.setAttribute("message", "Signup failed, please try again.");
                    response.sendRedirect(request.getContextPath() + "/signup.jsp");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(signupServlet.class.getName()).log(Level.SEVERE, null, ex);
            // On exception, set error message and redirect back to signup.jsp
            request.getSession().setAttribute("message", "An error occurred: " + ex.getMessage());
            response.sendRedirect(request.getContextPath() + "/signup.jsp");
        }
    }
}

package student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import Utils.ServletUtils;
import business.User;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "loginServlet", urlPatterns = {"/loginServlet"})
public class loginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = ServletUtils.getDBConnection()) {

            String regNo = request.getParameter("regno");
            String password = request.getParameter("password");

            String sql = "SELECT firstname, password FROM student WHERE regno = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, regNo);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    String storedHashedPassword = rs.getString("password");
                    String firstName = rs.getString("firstname");

                    if (BCrypt.checkpw(password, storedHashedPassword)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("student", new User(regNo, firstName));
                        session.setMaxInactiveInterval(30 * 60); 

    
                        Cookie ck = new Cookie("student", regNo);
                        ck.setHttpOnly(true); 
                        ck.setSecure(true); 
                        ck.setMaxAge(30 * 60); 
                        response.addCookie(ck);

                        response.sendRedirect(request.getContextPath() + "/studentDashboard.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/login.jsp?error=1");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/login.jsp?error=User not found");
                }
            }
        } catch (SQLException ex) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Database error");
        }
    }
}

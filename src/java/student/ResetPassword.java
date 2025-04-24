package student;

import java.io.IOException;
import java.sql.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Utils.ServletUtils;
import org.mindrot.jbcrypt.BCrypt;

public class ResetPassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String regNo = request.getParameter("regno");
        String newPassword = request.getParameter("new_password");
        String confirmPassword = request.getParameter("confirm_password");

        if (regNo == null || regNo.isEmpty() || 
            newPassword == null || newPassword.isEmpty() ||
            confirmPassword == null || confirmPassword.isEmpty() ||
            !newPassword.equals(confirmPassword)) {
            
            response.sendRedirect("resetPassword.jsp?error=Passwords do not match or fields are empty");
            return;
        }

        // Hash the password only after validation
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        try (Connection conn = ServletUtils.getDBConnection()) {
            String sql = "UPDATE student SET password = ? WHERE regno = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, hashedPassword);  // store the hashed password
                stmt.setString(2, regNo);
                int updated = stmt.executeUpdate();

                if (updated > 0) {
                    response.sendRedirect("login.jsp?message=Password successfully reset");
                } else {
                    response.sendRedirect("resetPassword.jsp?error=Student not found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("resetPassword.jsp?error=Database error");
        }
    }
}

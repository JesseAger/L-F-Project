package Items;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Utils.ServletUtils;

@WebServlet("/ClaimItemServlet")
public class ClaimItemServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve form parameters
        String reg_no = request.getParameter("reg_no");
        String claim_date = request.getParameter("claim_date");
        String item_no = request.getParameter("item_no");
        
        // Validate the inputs (optional but recommended)
        if (reg_no == null || claim_date == null || item_no == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing form data");
            return;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            // Get database connection using the utility class
            conn = ServletUtils.getDBConnection();
            
            // Generate a unique claim ID (you can also use auto-increment or any other method)
            String claim_id = UUID.randomUUID().toString().substring(0, 5); // Generate a random claim ID (or use auto-increment)
            
            // SQL query to insert claim into the database
            String sql = "INSERT INTO claims (claim_id, item_no, reg_no, claim_date, status) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, claim_id);
            stmt.setString(2, item_no);
            stmt.setString(3, reg_no);
            stmt.setString(4, claim_date);
            stmt.setString(5, "pending"); // Default status is "pending"
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                // Successfully added the claim
                response.sendRedirect("dashboard.jsp?message=Claim Submitted Successfully");
            } else {
                // Something went wrong
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to submit claim");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

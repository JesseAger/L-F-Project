package Items;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import business.User;

public class ClaimItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get the session and student details
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            // If there's no session or student, redirect to login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Retrieve student reg_no from session
        User student = (User) session.getAttribute("student");
        String reg_no = student.getUsername(); // Assuming the `User` class has a `getRegNo()` method
        
        // Retrieve item_no from the request (you should be passing this from the frontend when the button is clicked)
        String item_no = request.getParameter("item_no");
        
        // Get current date for claim_date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String claim_date = dateFormat.format(new Date()); // Get today's date
        
        // Database connection (Assuming JDBC is used)
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            // Database connection setup (Ensure you have correct credentials and DB URL)
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password");
            
            // SQL query to insert into the claims table
            String sql = "INSERT INTO claims (item_no, reg_no, claim_date) VALUES (?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, item_no);  // Set item_no
            stmt.setString(2, reg_no);   // Set reg_no
            stmt.setString(3, claim_date); // Set claim_date
            
            // Execute update
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                // If the insert was successful, redirect to the dashboard or another page
                response.sendRedirect(request.getContextPath() + "/student-dashboard.jsp");
            } else {
                // If something went wrong, notify the user
                request.setAttribute("error", "Failed to claim the item.");
                request.getRequestDispatcher("/student-dashboard.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
            request.setAttribute("error", "Database error occurred.");
            request.getRequestDispatcher("/student-dashboard.jsp").forward(request, response);
        } finally {
            // Clean up database resources
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

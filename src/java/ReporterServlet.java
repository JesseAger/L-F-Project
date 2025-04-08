import Response.ResponseBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utils.ServletUtils;



public class ReporterServlet extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
         try {
            PrintWriter out = ServletUtils.getPrintWriter(response);
            Connection conn = ServletUtils.getDBConnection();
             
             String regNo = request.getParameter("reg_no");
             String phoneNumber = request.getParameter("phone_number");
             String email = request.getParameter("email");
             String locationLost = request.getParameter("location_lost");
             String itemLost = request.getParameter("item_lost");
             String Description = request.getParameter("description");
             String dateLost = request.getParameter("date_lost");
             
            // Check if the report already exists
            String checkSql = "SELECT * FROM reporters WHERE reg_no = ? AND phone_number = ? AND email = ? AND location_lost = ? AND item_lost = ? AND description = ? AND date_lost = ?";
            try (PreparedStatement checkStatement = conn.prepareStatement(checkSql)) {
                checkStatement.setString(1, regNo);
                checkStatement.setString(2, phoneNumber);
                checkStatement.setString(3, email);
                checkStatement.setString(4, locationLost);
                checkStatement.setString(5, itemLost);
                checkStatement.setString(6, Description);
                checkStatement.setString(7, dateLost);

                ResultSet rs = checkStatement.executeQuery();
                if (rs.next()) {
                    // Inform the reporter that the report already exists
                    ResponseBuilder.buildRedirectResponse(out, "Report already exists. Please do not repeat the same report.", "report.jsp");
                    return; // Exit the method to prevent duplicate insertion
                }
            }
             
             // Prepare SQL statement
             String sql = "INSERT INTO reporters (reg_no, phone_number, email, location_lost, item_lost, description, date_lost) VALUES (?, ?, ?, ?, ?, ?, ?)";
             
             // Set parameters and execute SQL statement
             try (PreparedStatement statement = conn.prepareStatement(sql)) {
                 statement.setString(1, regNo);
                 statement.setString(2, phoneNumber);
                 statement.setString(3, email);
                 statement.setString(4, locationLost);
                 statement.setString(5, itemLost);
                 statement.setString(6, Description);
                 statement.setString(7, dateLost);
                 
                 // Execute SQL statement
                 int rowsInserted = statement.executeUpdate();
                 if (rowsInserted > 0) {
                     // Display success message
                
                     ResponseBuilder.buildRedirectResponse(out, "Reporter details inserted successfully! Kindly follow up with the security department for verification and confirmation of your lost item.", "studentDashboard.jsp");
                     
                 } else {
                     ResponseBuilder.buildRedirectResponse(out, "Failed to insert reporter details. Please try again.", "report.jsp");
                 }
             }
             catch (SQLException ex) {
                 // Handle any SQL or class loading exceptions
                 Logger.getLogger(ReporterServlet.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
     catch (SQLException ex) {
         Logger.getLogger(ReporterServlet.class.getName()).log(Level.SEVERE, null, ex);
     }
 }

}
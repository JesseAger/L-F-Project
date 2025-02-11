import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.*;
import Utils.ServletUtils;

@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"report.csv\"");

        try (PrintWriter writer = response.getWriter()) {
            // Write header row for items reported and found
            writer.println("Items Reported and Found");
            writer.println("Item Name, Description, Location Found, Date Found, Name, Contact Number, Email, Location Lost, Item Lost, Date Lost");

            try (Connection conn = ServletUtils.getDBConnection();
                    PreparedStatement recordsStatement = conn.prepareStatement("SELECT * FROM records");
                    ResultSet recordsResult = recordsStatement.executeQuery()) {

                // Write records data for items reported and found
                while (recordsResult.next()) {
                    // Retrieve data from reporters table for each record
                    String reporterQuery = "SELECT * FROM reporters WHERE item_lost = ? AND date_lost = ?";
                    try (PreparedStatement reportersStatement = conn.prepareStatement(reporterQuery)) {
                        reportersStatement.setString(1, recordsResult.getString("item_name"));
                        reportersStatement.setString(2, recordsResult.getString("date_found"));
                        ResultSet reportersResult = reportersStatement.executeQuery();

                        if (reportersResult.next()) {
                            writer.print(recordsResult.getString("item_name") + ",");
                            writer.print(recordsResult.getString("description") + ",");
                            writer.print(recordsResult.getString("location_found") + ",");
                            writer.print(recordsResult.getString("date_found") + ",");
                            writer.print(reportersResult.getString("name") + ",");
                            writer.print(reportersResult.getString("contact_number") + ",");
                            writer.print(reportersResult.getString("email") + ",");
                            writer.print(reportersResult.getString("location_lost") + ",");
                            writer.print(reportersResult.getString("item_lost") + ",");
                            writer.print(reportersResult.getString("date_lost"));
                            writer.println(); // End of row
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            // Write header row for items not collected
            writer.println("\nItems Not Collected");
            writer.println("Item Name, Description, Location Found, Date Found");

            try (Connection conn = ServletUtils.getDBConnection();
                    PreparedStatement recordsStatement = conn.prepareStatement("SELECT * FROM records");
                    ResultSet recordsResult = recordsStatement.executeQuery()) {

                // Write records data for items not collected
                while (recordsResult.next()) {
                    // Retrieve data from reporters table for each record
                    String reporterQuery = "SELECT * FROM reporters WHERE item_lost = ? AND date_lost = ?";
                    try (PreparedStatement reportersStatement = conn.prepareStatement(reporterQuery)) {
                        reportersStatement.setString(1, recordsResult.getString("item_name"));
                        reportersStatement.setString(2, recordsResult.getString("date_found"));
                        ResultSet reportersResult = reportersStatement.executeQuery();

                        if (!reportersResult.next()) {
                            writer.print(recordsResult.getString("item_name") + ",");
                            writer.print(recordsResult.getString("description") + ",");
                            writer.print(recordsResult.getString("location_found") + ",");
                            writer.print(recordsResult.getString("date_found"));
                            writer.println(); // End of row
                        }
                    }
                }
            } catch (SQLException ex) {
            }

            // Write header row for items reported but not found
            writer.println("\nItems Reported but Not Found");
            writer.println("Item Name, Description, Location Lost, Item Lost, Date Lost");

            try (Connection conn = ServletUtils.getDBConnection();
                    PreparedStatement reportersStatement = conn.prepareStatement("SELECT * FROM reporters");
                    ResultSet reportersResult = reportersStatement.executeQuery()) {

                // Write records data for items reported but not found
                while (reportersResult.next()) {
                    // Retrieve data from records table for each record
                    String recordsQuery = "SELECT * FROM records WHERE item_name = ? AND date_found = ?";
                    try (PreparedStatement recordsStatement = conn.prepareStatement(recordsQuery)) {
                        recordsStatement.setString(1, reportersResult.getString("item_lost"));
                        recordsStatement.setString(2, reportersResult.getString("date_lost"));
                        ResultSet recordsResult = recordsStatement.executeQuery();

                        if (!recordsResult.next()) {
                            writer.print(reportersResult.getString("item_lost") + ",");
                            writer.print(reportersResult.getString("description") + ",");
                            writer.print(reportersResult.getString("location_lost") + ",");
                            writer.print(reportersResult.getString("item_lost") + ",");
                            writer.print(reportersResult.getString("date_lost"));
                            writer.println(); // End of row
                        }
                    }
                }
            } catch (SQLException ex) {
            }
        }
    }
}

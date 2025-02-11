import Response.ResponseBuilder;
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.*;
import Utils.ServletUtils;

@WebServlet("/RecordsServlet")
@MultipartConfig(maxFileSize = 1024*1024*5, // 5 MB
                 maxRequestSize = 1024*1024*10) // 10 MB
public class RecordsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter out = ServletUtils.getPrintWriter(response);
            Connection conn = ServletUtils.getDBConnection();
            
            // Check if the uploaded file exceeds the maximum file size
            try {
                Part filePart = request.getPart("image");
                long fileSize = filePart.getSize();
                if (fileSize > 1024*1024*5) { // If file size exceeds 5 MB
                    ResponseBuilder.buildRedirectResponse(out, "File size exceeds the maximum limit of 5 MB.", "records.jsp");
                    return;
                }
            } catch (IllegalStateException e) {
                // If IllegalStateException occurs, it means the file size exceeds the maximum limit
                ResponseBuilder.buildRedirectResponse(out, "File size exceeds the maximum limit of 5 MB.", "records.jsp");
                return;
            }
            
            // Retrieve data from request parameters
            String itemName = request.getParameter("item_name");
            String description = request.getParameter("description");
            String locationFound = request.getParameter("location_found");
            String dateFound = request.getParameter("date_found");
            
            // Handle file upload
            Part filePart = request.getPart("image");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            
            // Save the uploaded file to the server
            String uploadDir = getServletContext().getRealPath("/images"); // Directory to save images
            Path filePath = Paths.get(uploadDir, fileName);
            
            // Check if the file already exists
            if (Files.exists(filePath)) {
                // If the file already exists, generate a new file name
                String timestamp = Long.toString(System.currentTimeMillis());
                fileName = timestamp + "_" + fileName;
                filePath = Paths.get(uploadDir, fileName);
            }
            
            Files.copy(fileContent, filePath);
            String imagePath = filePath.toString();
            
            // Prepare SQL statement
            String sql = "INSERT INTO records (item_name, description, location_found, date_found, image_path) VALUES (?, ?, ?, ?, ?)";
            // Set parameters
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Set parameters
                statement.setString(1, itemName);
                statement.setString(2, description);
                statement.setString(3, locationFound);
                statement.setString(4, dateFound);
                statement.setString(5, imagePath);
                // Execute SQL statement
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    ResponseBuilder.buildRedirectResponse(out, "Record inserted successfully!", "records.jsp");
                } else {
                    ResponseBuilder.buildRedirectResponse(out, "Failed to insert record. Please try again.", "records.jsp");
                }
                // Close resources
            } catch (SQLException ex) {
                // Handle any SQL exceptions
                response.getWriter().println("<h2>Database Error. Please try again later.</h2>");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecordsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

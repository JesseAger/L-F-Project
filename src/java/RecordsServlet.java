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

        // Retrieve form data
        String itemName = request.getParameter("item_name");
        String description = request.getParameter("description");
        String locationFound = request.getParameter("location_found");
        String dateFound = request.getParameter("date_found");

        // Handle file upload
        Part filePart = request.getPart("image");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        InputStream fileContent = filePart.getInputStream();

        // Define upload directory manually (adjust this path as needed)
        String uploadDir = "C:\\Users\\Vintage\\Desktop\\project1\\WebApp\\web\\uploads"; // Change this to match your setup
        File uploadDirectory = new File(uploadDir);

        // Debugging: Check if directory exists
        if (!uploadDirectory.exists()) {
            boolean created = uploadDirectory.mkdirs();
            System.out.println("Uploads directory created: " + created);
        } else {
            System.out.println("Uploads directory already exists.");
        }

        // Generate unique filename
        String timestamp = Long.toString(System.currentTimeMillis());
        String uniqueFileName = timestamp + "_" + fileName;
        File file = new File(uploadDirectory, uniqueFileName);

        // Save file to server
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileContent.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            System.out.println("File successfully saved: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("File writing error: " + e.getMessage());
        }

        // Store relative path in database
        String imagePath = "uploads/" + uniqueFileName;

        // Prepare SQL statement
        String sql = "INSERT INTO records (item_name, description, location_found, date_found, image_path) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, itemName);
            statement.setString(2, description);
            statement.setString(3, locationFound);
            statement.setString(4, dateFound);
            statement.setString(5, imagePath);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResponseBuilder.buildRedirectResponse(out, "Record inserted successfully!", "adminDashboard.jsp");
            } else {
                ResponseBuilder.buildRedirectResponse(out, "Failed to insert record. Please try again.", "adminDashboard.jsp");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.getWriter().println("<h2>Database Error: " + ex.getMessage() + "</h2>");
        }
    } catch (SQLException ex) {
        Logger.getLogger(RecordsServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}


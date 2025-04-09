
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
import java.sql.ResultSet;



@WebServlet("/ReporterServlet")
@MultipartConfig(maxFileSize = 1024*1024*5, maxRequestSize = 1024*1024*10)
public class ReporterServlet extends HttpServlet {

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

            // Handle image upload
            Part filePart = request.getPart("image");
            String imagePath = null;
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                InputStream fileContent = filePart.getInputStream();

                String uploadDir = "C:\\Users\\Vintage\\Desktop\\project1\\WebApp\\web\\uploads"; 
                File uploadDirectory = new File(uploadDir);
                if (!uploadDirectory.exists()) uploadDirectory.mkdirs();

                String timestamp = Long.toString(System.currentTimeMillis());
                String uniqueFileName = timestamp + "_" + fileName;
                File file = new File(uploadDirectory, uniqueFileName);

                try (FileOutputStream fos = new FileOutputStream(file);
                     BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileContent.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                }

                imagePath = "uploads/" + uniqueFileName;
            }

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
                    ResponseBuilder.buildRedirectResponse(out, "Report already exists. Please do not repeat the same report.", "report.jsp");
                    return;
                }
            }

            // Updated SQL with image_path
            String sql = "INSERT INTO reporters (reg_no, phone_number, email, location_lost, item_lost, description, date_lost, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, regNo);
                statement.setString(2, phoneNumber);
                statement.setString(3, email);
                statement.setString(4, locationLost);
                statement.setString(5, itemLost);
                statement.setString(6, Description);
                statement.setString(7, dateLost);
                statement.setString(8, imagePath); // image path can be null

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    ResponseBuilder.buildRedirectResponse(out, "Reporter details inserted successfully! Kindly follow up with the security department for verification and confirmation of your lost item.", "studentDashboard.jsp");
                } else {
                    ResponseBuilder.buildRedirectResponse(out, "Failed to insert reporter details. Please try again.", "report.jsp");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReporterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReporterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

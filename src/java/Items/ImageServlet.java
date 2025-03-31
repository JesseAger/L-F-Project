package Items;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/uploads/*")
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Ensure request is valid
        String requestedImage = request.getPathInfo();
        if (requestedImage == null || requestedImage.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid image request");
            return;
        }

        requestedImage = requestedImage.substring(1); // Remove leading "/"
        File file = new File("C:/Users/Vintage/Desktop/project1/WebApp/build/web/uploads", requestedImage);

        // Check if file exists
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
            return;
        }

        // Set proper MIME type
        String mimeType = getServletContext().getMimeType(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);

        // Serve the image
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error reading image");
        }
    }
}

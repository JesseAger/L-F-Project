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
    private static final String IMAGE_DIRECTORY = "C:/Users/Vintage/Desktop/project1/WebApp/web/uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestedImage = request.getPathInfo();

        // Validate request
        if (requestedImage == null || requestedImage.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid image request");
            return;
        }

        // Remove leading "/" and construct file path
        requestedImage = requestedImage.substring(1);
        File file = new File(IMAGE_DIRECTORY, requestedImage);

        // Check if file exists
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
            return;
        }

        // Set MIME type
        String mimeType = getServletContext().getMimeType(file.getName());
        if (mimeType == null) {
            mimeType = "image/jpeg"; // Default MIME type for images
        }
        response.setContentType(mimeType);
        
        // Enable caching
        response.setHeader("Cache-Control", "public, max-age=86400");

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

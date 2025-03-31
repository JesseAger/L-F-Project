package Items;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import Utils.ServletUtils;

@WebServlet(name = "foundItemsServlet", urlPatterns = {"/foundItemsServlet"})
public class foundItemsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<FoundItem> foundItems = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ServletUtils.getDBConnection();
            String sql = "SELECT item_name, description, image AS image_path FROM records WHERE status = 'found'"; 
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                foundItems.add(new FoundItem(
                        rs.getString("item_name"),
                        rs.getString("description"),
                        rs.getString("image_path") // Ensure column exists
                ));
            }

            // Log fetched items count for debugging
            System.out.println("Items retrieved: " + foundItems.size());

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
            return;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        // Convert list to JSON
        String json = new Gson().toJson(foundItems);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    // Static inner class for FoundItem to avoid serialization issues
    static class FoundItem {
        private String itemName;
        private String description;
        private String imagePath;

        public FoundItem(String itemName, String description, String imagePath) {
            this.itemName = itemName;
            this.description = description;
            this.imagePath = imagePath;
        }
    }
}

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
            String sql = "SELECT item_name, description, image_path FROM records WHERE status = 'found'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                foundItems.add(new FoundItem(
                        rs.getString("item_name"),
                        rs.getString("description"),
                        rs.getString("image_path")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
            return;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        String json = new Gson().toJson(foundItems);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    // Inner class for Found Item
    class FoundItem {
        private String itemName;
        private String description;
        private String image;

        public FoundItem() {
        }

        public FoundItem(String itemName, String description, String image) {
            this.itemName = itemName;
            this.description = description;
            this.image = image;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
        
        
        
    }
}

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

@WebServlet(name = "lostItemServlet", urlPatterns = {"/lostItemServlet"})
public class lostItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<LostItem> lostItems = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ServletUtils.getDBConnection();
            String sql = "SELECT item_lost, description, image_path FROM reporters WHERE status = 'lost'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                lostItems.add(new LostItem(
                        rs.getString("item_lost"),
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

        String json = new Gson().toJson(lostItems);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    // Inner class for Found Item
    class LostItem {
        private String item_lost;
        private String description;
        private String image;

        public LostItem() {
        }

        public LostItem(String item_lost, String description, String image) {
            this.item_lost = item_lost;
            this.description = description;
            this.image = image;
        }

        public String getitem_lost() {
            return item_lost;
        }

        public void setitem_lost(String item_lost) {
            this.item_lost = item_lost;
        }

        public String getdescription() {
            return description;
        }

        public void setdescription(String description) {
            this.description = description;
        }

        public String getimage() {
            return image;
        }

        public void setimage(String image) {
            this.image = image;
        }      
    }
}

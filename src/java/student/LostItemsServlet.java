package student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.google.gson.Gson;
import Utils.ServletUtils;

@WebServlet("/LostItemsServlet")
public class LostItemsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Connection conn = ServletUtils.getDBConnection();
            String sql = "SELECT item_lost, location_lost, date_lost, image_path FROM reporters ORDER BY date_lost DESC";

            List<Item> lostItems = new ArrayList<>();
            try (PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(
                        rs.getString("item_lost"),
                        rs.getString("location_lost"),
                        rs.getString("date_lost"),
                        rs.getString("image_path")
                    );
                    lostItems.add(item);
                }
            }

            Gson gson = new Gson();
            String json = gson.toJson(lostItems);
            out.print(json);
            out.flush();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    class Item {
        String item_lost, location_lost, date_lost, image_path;
        
        public Item(String item_lost, String location_lost, String date_lost, String image_path) {
            this.item_lost = item_lost;
            this.location_lost = location_lost;
            this.date_lost = date_lost;
            this.image_path = image_path;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Items;

import Utils.ServletUtils;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vintage
 */
public class findclaimeditems extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<foundItem> foundItems = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ServletUtils.getDBConnection();
            String sql = "SELECT * FROM records WHERE claimed = 'true'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                foundItems.add(new foundItem(
                        rs.getString("item_name"),
                        rs.getString("description"),
                        rs.getString("image_path"),
                        rs.getInt("record_id")
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
    
    
   class foundItem {
        private String itemName;
        private String description;
        private String image;
        private int item_no; 
        
        public foundItem() {
        }

     

        public foundItem(String itemname, String description, String image,int item_no) {
             itemName = itemname;
            this.description = description;
            this.image = image;
            this.item_no = item_no;
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
           public int getItem_no() {
            return item_no;
        }

        public void setItem_no(int item_no) {
            this.item_no = item_no;
        }
        
        
    }
}

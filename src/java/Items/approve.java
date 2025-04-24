/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Items;

import Utils.ServletUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vintage
 */
public class approve extends HttpServlet {
        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
         PrintWriter out = response.getWriter();
          
            String item_no= request.getParameter("Valuep");
                 
            Connection conn = null;
            PreparedStatement statement = null;
    
         HttpSession session = request.getSession(false); // false means don't create a new session if one doesn't exist
        if (session != null) {
            
             try {
              
                 conn = ServletUtils.getDBConnection();
                 Date javaDate = new Date();
                 java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
                 
                 String sql = "Update records set approval=? where record_id=?";
                 statement = conn.prepareStatement(sql);
                 
                 
                 statement.setString(1, "true");
                 statement.setString(2, item_no);
              
                 
                 
                 int rowsInserted = statement.executeUpdate();
                 if (rowsInserted > 0) {
                     out.print("Approval added for item "+ item_no);
                 } else {
                     out.print("error");
                 } } catch (SQLException ex) {
                 Logger.getLogger(claimeditems.class.getName()).log(Level.SEVERE, null, ex);
             }
           
           
           
        } else {
            
        }
        
        
        
        

        
        
        
    }
}

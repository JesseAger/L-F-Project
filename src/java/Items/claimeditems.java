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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vintage
 */
public class claimeditems extends HttpServlet {

  
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet claimeditems</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet claimeditems at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
         PrintWriter out = response.getWriter();
         
         
         
          
            String item_no= request.getParameter("pValue");
                 
            Connection conn = null;
            PreparedStatement statement = null;
           
               
        
         
         
         
         
         HttpSession session = request.getSession(false); // false means don't create a new session if one doesn't exist
        if (session != null) {
            
             try {
                 String reg_no=(String) session.getAttribute("Reg_no");
                 conn = ServletUtils.getDBConnection();
                 Date javaDate = new Date();
                 java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
                 
                 String sql = "INSERT INTO claims (item_no, reg_no, claim_date, status) VALUES (?, ?, ?, ?)";
                 statement = conn.prepareStatement(sql);
                 
                 
                 statement.setString(1, item_no);
                 statement.setString(2, reg_no);
                 statement.setDate(3, sqlDate);
                 statement.setString(4, "pending");
                 
                 
                 int rowsInserted = statement.executeUpdate();
                 if (rowsInserted > 0) {
                     out.print("claim added for item "+ item_no);
                 } else {
                     out.print("error");
                 } } catch (SQLException ex) {
                 Logger.getLogger(claimeditems.class.getName()).log(Level.SEVERE, null, ex);
             }
           
           
           
        } else {
            
        }
        
        
        
        

        
        
        
    }

  
}


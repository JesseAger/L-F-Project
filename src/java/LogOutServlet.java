import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.PrintWriter;

public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = null;
        // Retrieve the current session, false means do not create a new session if it doesn't exist
        HttpSession session = request.getSession(false);
        if (session != null) {
            userName = (String) session.getAttribute("user");
            session.invalidate(); // Invalidate the session if it exists
        }
        
        // Delete the cookie by setting its maximum age to 0
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("user")) {
//                    userName = cookie.getValue();
//                    cookie.setMaxAge(0);
//                    response.addCookie(cookie); 
//                    break;
//                }
//            }
//        }
//        
        // Redirect the user to the login page
        response.setContentType("text/html");
        
        // Get the PrintWriter to write out HTML
        PrintWriter out = response.getWriter();
        
        // Output HTML content
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Logout</title></head>");
        out.println("<body>");
        out.println("<script type='text/javascript'>");
        if (userName != null) {
            out.println("alert('" + userName + ", you are successfully logged out!');");
        }
        else {
            out.println("alert('You are successfully logged out!');");
        }
        out.println("window.location = 'admin-login.html';"); // Use window.location for redirection
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
        
    }
}

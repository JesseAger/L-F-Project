import Response.ResponseBuilder;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.PrintWriter;
import business.User;

public class LogOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = null;
        // Retrieve the current session, false means do not create a new session if it doesn't exist
        HttpSession session = request.getSession(false);
        if (session != null) {
            user = (User) session.getAttribute("user");
            session.invalidate(); // Invalidate the session if it exists
        }
        
        // Delete the cookie by setting its maximum age to 0
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
//                    userName = cookie.getValue();
                    cookie.setMaxAge(0);
                    response.addCookie(cookie); 
                    break;
                }
            }
        }
        
        // Redirect the user to the login page
        response.setContentType("text/html");
        
        // Get the PrintWriter to write out HTML
        PrintWriter out = response.getWriter();
        
        // Output HTML content
        ResponseBuilder.buildRedirectResponse(out, (user != null ? user.getUsername() + ", you are successfully logged out!" : "You are successfully logged out!"), "admin-login.jsp");
        
    }
}

import Response.ResponseBuilder;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.PrintWriter;
import business.User;

public class logoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User student = null;

        // Retrieve the current session, false means do not create a new session if it doesn't exist
        HttpSession session = request.getSession(false);
        if (session != null) {
            student = (User) session.getAttribute("student");
            // Invalidate the session if it exists
            session.invalidate(); 
        }

        // Delete the student authentication cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("student")) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");  // Ensure it removes from all paths
                    response.addCookie(cookie);
                    break;
                }
            }
        }

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        ResponseBuilder.buildRedirectResponse(out, 
            (student != null ? student.getUsername() + ", you are successfully logged out!" 
                             : "You are successfully logged out!"), 
            "login.jsp");
    }
}

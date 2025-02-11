//package filters;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import java.io.IOException;
//
//public class SessionFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        // Check if the request is for the login page
//        String loginURI = httpRequest.getContextPath() + "/admin-login.html";
//        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
//
//        HttpSession session = httpRequest.getSession(false); // Do not create a new session if it doesn't exist
//
//        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
//
//        if (isLoggedIn || isLoginRequest) {
//            // User is logged in or it's a login page request, allow the request to proceed
//            chain.doFilter(request, response);
//        } else {
//            // User is not logged in and it's not a login page request, redirect to the login page
//            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin-login.html");
//        }
//    }
//
//    // Other methods of Filter interface
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Initialization code, if needed
//    }
//
//    @Override
//    public void destroy() {
//        // Cleanup code, if needed
//    }
//}

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Invalidate the current session
    if(session != null) {
        session.invalidate();
    }
    // Redirect to the login page
    response.sendRedirect(request.getContextPath() + "/login.jsp");
%>

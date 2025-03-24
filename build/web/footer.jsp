
<%@ page import="java.util.*" %>
<%
// Initialize the current year that's used in the copyright notice
GregorianCalendar currentDate = new GregorianCalendar();
int currentYear = currentDate.get(Calendar.YEAR);
%>

<p><small>&copy; Copyright <%= currentYear %> Lost and Found System. All rights reserved</small></p>

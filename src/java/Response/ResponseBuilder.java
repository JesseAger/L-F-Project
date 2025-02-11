/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Response;
import java.io.PrintWriter;
/**
 *
 * @author FABIUS
 */
public class ResponseBuilder {
    // ResponseBuilder.java
    // Builds a response with a JavaScript pop-up message and redirects to a given URL
    public static void buildRedirectResponse(PrintWriter out, String message, String redirectUrl) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Redirect</title></head>");
        out.println("<body>");
        out.println("<script type='text/javascript'>");
        out.println("setTimeout(function() {");
        out.println("    alert('" + message + "');");
        out.println("    window.location = '" + redirectUrl + "';");
        out.println("}, 3000);");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
    }
}


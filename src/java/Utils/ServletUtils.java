package Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jakarta.servlet.http.HttpServletResponse;

public class ServletUtils {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/lost_and_founddb4";
    private static final String USER = "root";
    private static final String PASS = "fabius001";

    static {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
    }

    public static Connection getDBConnection() throws SQLException {
        // Setup the connection with the DB
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static PrintWriter getPrintWriter(HttpServletResponse response) throws IOException {
        // Set response content type
        response.setContentType("text/html;charset=UTF-8");
        return response.getWriter();
    }
}

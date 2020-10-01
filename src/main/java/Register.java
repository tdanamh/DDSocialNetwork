import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/register")
public class Register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName").substring(0,1).toUpperCase() + req.getParameter("firstName").substring(1);
        String lastName = req.getParameter("lastName").substring(0,1).toUpperCase() + req.getParameter("lastName").substring(1);
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String gender = req.getParameter("gender");

        req.setAttribute("lastName", lastName);
        req.setAttribute("firstName", firstName);
        req.setAttribute("email", email);

        //calling sanitizer
        if(!errorCheck(firstName, lastName, email, password, gender, req, resp)){
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(req, resp);
        }
        else {

            String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://localhost/users";

            String USER = "root";
            String PASS = "";

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
                String sql = "insert into users(firstName, lastName, email, password, gender) values(?,?,?,?,?)";

                stmt = conn.prepareStatement(sql);

                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, email);
                stmt.setString(4, password);
                stmt.setString(5, gender);

                stmt.executeUpdate();
                stmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login");
            requestDispatcher.forward(req, resp);
        }
    }

    private boolean errorCheck(String firstName, String lastName, String email, String password, String gender, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(firstName.length() < 2){
            req.setAttribute("errorMessage", "Your first name must be at least 2 characters long");
            return false;
        }
        if(!firstName.matches("^[a-zA-Z0-9]*$")){
            req.setAttribute("errorMessage", "Use only alphanumeric  characters");
            return false;
        }
        if (lastName.length() < 2) {
            req.setAttribute("errorMessage", "Your last name must be at least 2 characters long");
            return false;
        }
        if(!lastName.matches("^[a-zA-Z0-9]*$")){
            req.setAttribute("errorMessage", "Use only alphanumeric  characters");
            return false;
        }
        if(password.length() < 8){
            req.setAttribute("errorMessage", "Your password must be at least 8 characters long");
            return false;
        }
        if(!password.matches("^[a-zA-Z0-9]*$")){
            req.setAttribute("errorMessage", "Use only alphanumeric  characters");
            return false;
        }
        return true;
    }

}

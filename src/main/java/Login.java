import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = "/login")
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/users";

        String USER = "root";
        String PASS = "";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql = "SELECT id, email, password from users WHERE email = ? and password = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if(!rs.next()){
                req.setAttribute("loginErrorMessage", "Incorrect username or password");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(req, resp);
            }
            else {
                User currentUser = new User(rs.getInt("id"));
                HttpSession session = req.getSession();

                session.setAttribute("fullName", currentUser.getName());
                session.setAttribute("userId", rs.getInt("id"));
                session.setAttribute("registerDate", currentUser.getRegisterDate());
                session.setAttribute("profilePicture",currentUser.getProfilePicture());

                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/home.jsp");
                requestDispatcher.forward(req, resp);
            }

            //Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

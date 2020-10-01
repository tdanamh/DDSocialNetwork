import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/LikePost")
public class LikePost extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/users";

        String USER = "root";
        String PASS = "";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String checkIfAlreadyLiked = "SELECT id from likes where userId = ? and postId = ?";

            stmt = conn.prepareStatement(checkIfAlreadyLiked);
            stmt.setString(1,req.getSession().getAttribute("userId").toString());
            stmt.setString(2 ,req.getParameter("id"));

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                String sql = "INSERT INTO LIKES VALUES(0,?,?)";

                stmt = conn.prepareStatement(sql);
                stmt.setString(1, req.getParameter("id"));
                stmt.setString(2, req.getSession().getAttribute("userId").toString());

                stmt.executeUpdate();
            }

            else {
                String sql = "DELETE from likes WHERE postId = ? and userId = ?";

                stmt = conn.prepareStatement(sql);
                stmt.setString(1, req.getParameter("id"));
                stmt.setString(2, req.getSession().getAttribute("userId").toString());

                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}

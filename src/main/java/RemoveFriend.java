import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/removeFriend")
public class RemoveFriend extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/users";

        String USER = "root";
        String PASS = "";

        Connection conn = null;
        PreparedStatement stmt = null;

        String friendId = req.getParameter("friendId");
        String userId = req.getSession().getAttribute("userId").toString();

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql = "delete from friendships where user = ? and friend = ? OR friend = ? and user = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1,userId);
            stmt.setString(2,friendId);
            stmt.setString(3,userId);
            stmt.setString(4,friendId);

            stmt.executeUpdate();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

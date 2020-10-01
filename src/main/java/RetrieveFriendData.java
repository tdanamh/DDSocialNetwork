import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/retrieveFriendData")
public class RetrieveFriendData extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String DB_URL = "jdbc:mysql://localhost/users";

        String USER = "root";
        String PASS = "";

        Connection conn = null;
        PreparedStatement stmt = null;

        String friendId  = req.getParameter("friendId");

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String sql = "SELECT CONCAT(CONCAT(firstName, ' '),lastName) as name, profilePicture, DATE_FORMAT(registerDate,'%D %M %Y') as registerDate,gender, (select count(id) from friendships where user=? or friend = ?) as noFriends from users where id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,friendId);
            stmt.setString(2,friendId);
            stmt.setString(3,friendId);

            ResultSet rs = stmt.executeQuery();
            JSONObject data = new JSONObject();

            while(rs.next()) {

                data.put("name",rs.getString("name"));
                data.put("noFriends",rs.getString("noFriends"));
                data.put("profilePicture",rs.getString("profilePicture"));
                data.put("registerDate",rs.getString("registerDate"));
                data.put("gender",rs.getString("gender"));
            }

            resp.getWriter().print(data);

            rs.close();
            conn.close();
            stmt.close();

        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }

    }
}

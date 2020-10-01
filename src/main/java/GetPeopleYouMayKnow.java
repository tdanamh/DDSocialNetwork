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

@WebServlet(urlPatterns = "/getPeopleYouMayKnow")
public class GetPeopleYouMayKnow extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/users";

        String USER = "root";
        String PASS = "";

        Connection conn = null;
        PreparedStatement stmt = null;

        JSONArray notFriends = new JSONArray();

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql = "SELECT CONCAT(CONCAT(firstName, ' '),lastName) as name, id from users where id != ? and id NOT IN (SELECT friend FROM friendships WHERE user = ? UNION SELECT user FROM friendships WHERE friend = ?) limit 3";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, req.getSession().getAttribute("userId").toString());
            stmt.setString(2, req.getSession().getAttribute("userId").toString());
            stmt.setString(3, req.getSession().getAttribute("userId").toString());

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                JSONObject notFriend = new JSONObject();
                notFriend.put("name", rs.getString("name"));
                notFriend.put("id", rs.getString("id"));
                notFriends.put(notFriend);
            }


            resp.getWriter().print(notFriends);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

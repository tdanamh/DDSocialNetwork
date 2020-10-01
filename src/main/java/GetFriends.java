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

@WebServlet(urlPatterns = "/getFriends")
public class GetFriends extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String DB_URL = "jdbc:mysql://localhost/users";

        String USER = "root";
        String PASS = "";

        Connection conn = null;
        PreparedStatement stmt = null;

        JSONArray dataArray = new JSONArray();

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String sql = "SELECT CONCAT(CONCAT(firstName, ' '),lastName) as name, id from users where id != ? and id IN (SELECT friend FROM friendships WHERE user = ?  and status = 1 UNION SELECT user FROM friendships WHERE friend = ? and status = 1)";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, req.getSession().getAttribute("userId").toString());
            stmt.setString(2, req.getSession().getAttribute("userId").toString());
            stmt.setString(3, req.getSession().getAttribute("userId").toString());

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                JSONObject data = new JSONObject();
                data.put("name", rs.getString("name"));
                data.put("id", rs.getString("id"));

                dataArray.put(data);
            }

            resp.getWriter().print(dataArray);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
    }
}

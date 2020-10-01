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

@WebServlet(urlPatterns = "/retrieveComments")
public class RetrieveComments extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/users";

        String USER = "root";
        String PASS = "";

        Connection conn = null;
        PreparedStatement stmt = null;

        JSONArray array = new JSONArray();

        String postId = req.getParameter("id");

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql = "SELECT CONCAT(CONCAT(u.firstName, ' '),u.lastName) as author, c.text as text, unix_timestamp(now()) - unix_timestamp(publishDate) as publishDate from comments c join users u on c.userId = u.id where postId = ? order by c.id";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, postId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JSONObject eachPost = new JSONObject();
                eachPost.put("author", rs.getString("author"));
                eachPost.put("text", rs.getString("text"));
                eachPost.put("publishDate", calculateTime(Integer.parseInt(rs.getString("publishDate"))));

                array.put(eachPost);

                //System.out.println(rs.getString("author") + rs.getString("text"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
        resp.getWriter().print(array);
    }

    String calculateTime(int time){
        String timeAgo;

        int days = time / 86_400;
        int hours = time / 3600;
        int minutes = time / 60;

        if(days > 0){
            timeAgo = days + "d";
        }
        else if(hours > 0){
            timeAgo = hours + "h";
        }
        else if(minutes > 0){
            timeAgo = minutes + "m";
        }
        else {
            timeAgo = time + "s";
        }


        return timeAgo;
    }

}

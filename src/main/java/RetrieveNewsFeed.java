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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import static sun.jvm.hotspot.code.CompressedStream.H;

@WebServlet(urlPatterns = "/retrieveNewsFeed")
public class RetrieveNewsFeed extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/users";

        String USER = "root";
        String PASS = "";

        Connection conn = null;
        PreparedStatement stmt = null;

        JSONArray array = new JSONArray();

        String userId = req.getSession().getAttribute("userId").toString();

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String sql = "SELECT n.id as id, CONCAT(CONCAT(u.firstName, ' '), u.lastName) as author, n.text as text, unix_timestamp(now()) - unix_timestamp(n.publishDate) as publishDate from news n join users u on n.author=u.id where n.author IN (select user from friendships where friend = ? and status = 1 union select friend from friendships where user = ? and status = 1) OR n.author = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.setString(2, userId);
            stmt.setString(3, userId);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                JSONObject eachPost = new JSONObject();
                eachPost.put("author", rs.getString("author"));
                eachPost.put("text", rs.getString("text"));

                String publishDate = calculateTime(Integer.parseInt(rs.getString("publishDate")));

                eachPost.put("publishDate", publishDate);
                eachPost.put("postId",rs.getString("id"));

                array.put(eachPost);
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
            timeAgo = days + " days ago";
        }
        else if(hours > 0){
            timeAgo = hours + " hours ago";
        }
        else if(minutes > 0){
            timeAgo = minutes + " minutes ago";
        }
        else {
            timeAgo = time + " seconds ago";
        }
        return timeAgo;
    }

}

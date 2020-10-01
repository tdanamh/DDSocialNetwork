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
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

@WebServlet(urlPatterns = "/UpdatePostInfo")
public class UpdatePostInfo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/users";

        String USER = "root";
        String PASS = "";

        Connection conn = null;
        PreparedStatement stmt = null;

        JSONArray array = new JSONArray();

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql = "SELECT n.id as postId, COUNT(l.id) as count from news n left join likes l on n.id = l.postId group by n.id";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                JSONObject currentObject = new JSONObject();
                currentObject.put("postId", rs.getInt("postId"));
                currentObject.put("count", rs.getInt("count"));

//                get number of comments for each post
                String commentsQuery = "SELECT COUNT(id) as commentsNo from comments where postId = ?";
                PreparedStatement commentsStmt = conn.prepareStatement(commentsQuery);
                commentsStmt.setInt(1, rs.getInt("postId"));
                ResultSet commentsNumber = commentsStmt.executeQuery();

                while(commentsNumber.next()){
                    currentObject.put("comments", commentsNumber.getInt("commentsNo"));
                }

                array.put(currentObject);
            }

        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
        //System.out.println(array);
        resp.getWriter().print(array);
    }
}

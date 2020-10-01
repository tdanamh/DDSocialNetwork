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

@WebServlet(urlPatterns = "/getNotifications")
public class GetNotifications extends HttpServlet {

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
            String sql = "SELECT CONCAT(CONCAT(u.firstName, ' '), u.lastName) as name, f.id, f.startDate  from friendships f join users u on u.id = f.user where f.friend = ? and f.status = 0";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, req.getSession().getAttribute("userId").toString());

            ResultSet rs = stmt.executeQuery();

            JSONArray dataArray = new JSONArray();

            while(rs.next()){
                JSONObject data = new JSONObject();
                data.put("user", rs.getString("name"));
                data.put("id", rs.getString("f.id"));
                data.put("dataSent", rs.getDate("startDate"));
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

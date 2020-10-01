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

@WebServlet(urlPatterns = "/searchByName")
public class SearchByName extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String DB_URL = "jdbc:mysql://localhost/users";

        String USER = "root";
        String PASS = "";

        Connection conn = null;
        PreparedStatement stmt = null;

        String searchedName = req.getParameter("name") + "%";

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql = "SELECT CONCAT(CONCAT(firstName,' '), lastName) as name, id from users where lastName LIKE ? or firstName LIKE ? or CONCAT(CONCAT(firstName,' '), lastName) LIKE ? or CONCAT(CONCAT(lastName,' '), firstName) LIKE ? limit 10";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, searchedName);
            stmt.setString(2, searchedName);
            stmt.setString(3, searchedName);
            stmt.setString(4, searchedName);

            ResultSet rs = stmt.executeQuery();

            JSONArray dataArray = new JSONArray();

            while (rs.next()) {
                System.out.println(rs.getString("name"));
                JSONObject data = new JSONObject();
                data.put("id", rs.getInt("id"));
                data.put("name", rs.getString("name"));

                dataArray.put(data);
            }

            resp.getWriter().print(dataArray);

        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
    }
}

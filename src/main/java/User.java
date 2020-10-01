import javax.servlet.http.HttpServlet;
import java.sql.*;

public class User extends HttpServlet {

    String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    String DB_URL = "jdbc:mysql://localhost/users";

    String USER = "root";
    String PASS = "";

    Connection conn = null;
    PreparedStatement stmt = null;

    int id;
    String firstName = "";
    String lastName = "";
    String registerDate = null;
    String profilePicture= null;

    public User(int id) {
        this.id = id;
        setName();
        setRegisterDate();
        setProfilePicture();
    }

    public void setName(){
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql = "SELECT firstName, lastName from users WHERE id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRegisterDate(){

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT DATE_FORMAT(registerDate, '%d %M %Y' ) as registerDate from users WHERE id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                registerDate = rs.getString("registerDate");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void setProfilePicture() {
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql = "SELECT profilePicture as profilePicture from users WHERE id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                profilePicture = rs.getString("profilePicture");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String getProfilePicture(){
        return profilePicture;
    }
}

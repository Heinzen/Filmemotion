import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class sqlFactory {
    private final String psqlURL = "jdbc:postgresql://localhost:5432/filmemotion";

    public Connection createConnection(Properties connectionProps) {
        Connection conn = null;

        try{
            conn = DriverManager.getConnection(psqlURL, connectionProps.getProperty("user"), connectionProps.getProperty("pw"));
            System.out.println("Connected to filmemotion");
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public ResultSet selectQuery(Connection conn, String query) {
        Statement st;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
}
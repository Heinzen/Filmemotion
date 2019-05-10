import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;



public class Main {

    private static final int ID_INDEX = 1;
    private static final int TITLE_INDEX = 2;
    private static final int URL_INDEX = 3;

    public static void main(String[] args) {
        Properties connectionProps = new Properties();
        connectionProps.setProperty("user", "postgres");
        connectionProps.setProperty("pw", "postgres");

        sqlFactory psql = new sqlFactory();
        Connection conn = psql.createConnection(connectionProps);

        String query = "SELECT * FROM public.films;";
        ResultSet result = psql.selectQuery(conn, query);

        try{
            ResultSetMetaData meta = result.getMetaData();
            int columnCount = meta.getColumnCount();

            for (int i = 1; i <= columnCount; i++){
                String columnName = meta.getColumnName(i);
                result.next(); //.next() retrieves the next row
                System.out.println("Title: "+result.getString(TITLE_INDEX));
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}

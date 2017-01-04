import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by LunaFlores on 1/4/17.
 */
public class DatabaseUtils {
    private static DatabaseUtils ourInstance = new DatabaseUtils();

    public static DatabaseUtils getInstance() {
        return ourInstance;
    }

    private static final String USER_NAME = "myuser";
    private static final String PASSWORD = "mypass";
    private static final String DATABASE = "my";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final boolean USE_SSL = false;

    private static final String CONNECT = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + (USE_SSL ? "" : "?useSSL=false");

    private DatabaseUtils() {
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(CONNECT, USER_NAME, PASSWORD);
        //System.out.printf("Connected to %s%n", CONNECT);
        return conn;
    }
}

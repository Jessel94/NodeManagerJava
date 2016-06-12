package hro.ictlab.nodemanager.connectors;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class is used to setup and close all connections for the database.
 */
public class DbConnector {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://" + System.getenv("MYSQL") + "/NodeManager?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  database credentials
    private static final String USER = System.getenv("MYSQL_USER");
    private static final String PASS = System.getenv("MYSQL_PASS");


    /**
     * @return Returns a connection for the database
     */
    public Connection getConnection() throws Exception {
        //Register JDBC driver
        Class.forName(JDBC_DRIVER);

        //Open a connection
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * Close the database connection if it has been initialized
     */
    public void closeConnection(Connection conn) throws Exception {
        if (conn != null) {
            conn.close();
        }
    }
}

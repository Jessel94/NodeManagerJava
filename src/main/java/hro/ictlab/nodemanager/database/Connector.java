package hro.ictlab.nodemanager.database;

import java.sql.Connection;
import java.sql.DriverManager;

class Connector {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://" + System.getenv("MYSQL") + "/NodeManager?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  database credentials
    private static final String USER = System.getenv("MYSQL_USER");
    private static final String PASS = System.getenv("MYSQL_PASS");


    Connection getConnection() throws Exception {
        //STEP 1: Register JDBC driver
        Class.forName(JDBC_DRIVER);

        //STEP 2: Open a connection
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    void closeConnection(Connection conn) throws Exception {
        //finally block used to close resources
        if (conn != null) {
            conn.close();
        }
    }
}

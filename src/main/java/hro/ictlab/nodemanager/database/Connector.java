package hro.ictlab.nodemanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Connector {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://145.24.222.223:3306/NodeManager?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  database credentials
    private static final String USER = "0882805";
    private static final String PASS = "100ddJ";


    Connection GetConnection() throws Exception{
        //STEP 1: Register JDBC driver
        Class.forName(JDBC_DRIVER);

        //STEP 2: Open a connection
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn;
    }

    void CloseConnection(Connection conn) throws Exception{
        //finally block used to close resources
        if(conn!=null) {
            conn.close();
        }
    }
}

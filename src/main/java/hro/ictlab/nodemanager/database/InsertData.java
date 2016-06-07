package hro.ictlab.nodemanager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

class InsertData {

    PreparedStatement NewQueue(Connection connection) throws Exception
    {
        PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Queues (id, hostname, queuename, queuepass)" +
                    " VALUES (NULL, '" + System.getenv("RABBITMQ") + "', '" + System.getenv("RABBITMQ_USER") + "', '" + System.getenv("RABBITMQ_PASS") +"')", Statement.RETURN_GENERATED_KEYS
            );
        return ps;
    }

    ResultSet GetResultSet(PreparedStatement ps) throws Exception
    {
        ps.execute();
        return ps.getGeneratedKeys();
    }
}

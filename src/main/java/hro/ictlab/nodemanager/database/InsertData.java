package hro.ictlab.nodemanager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

class InsertData {

    PreparedStatement NewQueue2(Connection connection) throws Exception
    {
        PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Queues (id, hostname, queuename, queuepass)" +
                    " VALUES (NULL, '" + System.getenv("RABBITMQ") + "', '" + System.getenv("RABBITMQ_USER") + "', '" + System.getenv("RABBITMQ_PASS") +"')"
            );
        return ps;
    }

    void ExecuteUpdate(Statement statement, String sql) throws Exception {
        statement.executeUpdate(sql);
    }
}

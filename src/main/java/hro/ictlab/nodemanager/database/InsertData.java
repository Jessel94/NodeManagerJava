package hro.ictlab.nodemanager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

class InsertData {

    PreparedStatement newQueue(Connection connection, String hostName, String userName, String passWord) throws Exception {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Queues (id, hostname, queuename, queuepass)" +
                        " VALUES (NULL, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
        );
        ps.setString(1, hostName);
        ps.setString(2, userName);
        ps.setString(3, passWord);
        return ps;
    }

    PreparedStatement newNode(Connection connection, String name, int queueID) throws Exception {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Nodes (id, name, queueid, lastchecked)" +
                        " VALUES (NULL, ?, ?, NULL)"
        );
        ps.setString(1, name);
        ps.setInt(2, queueID);
        return ps;
    }

    PreparedStatement executeStatement(PreparedStatement ps) throws Exception {
        ps.execute();
        return ps;
    }

    ResultSet getResultSet(PreparedStatement ps) throws Exception {
        executeStatement(ps);
        return ps.getGeneratedKeys();
    }
}

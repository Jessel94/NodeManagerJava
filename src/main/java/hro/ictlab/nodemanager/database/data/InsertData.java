package hro.ictlab.nodemanager.database.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

class InsertData {

    PreparedStatement newContainer(Connection connection, String name, String state, String queueId) throws Exception {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Containers (id, name, creationdate, state, queueid)" +
                        " VALUES (NULL, ?, NULL, ?, ?)", Statement.RETURN_GENERATED_KEYS
        );
        ps.setString(1, name);
        ps.setString(2, state);
        ps.setInt(3, Integer.parseInt(queueId));
        return ps;
    }

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

    PreparedStatement newNode(Connection connection, String ip, int queueID) throws Exception {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Nodes (id, name, queueid, lastchecked)" +
                        " VALUES (NULL, ?, ?, NULL)"
        );
        ps.setString(1, ip);
        ps.setInt(2, queueID);
        return ps;
    }
}

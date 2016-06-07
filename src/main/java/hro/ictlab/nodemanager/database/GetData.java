package hro.ictlab.nodemanager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class GetData {

    PreparedStatement GetContainers(Connection connection, String containerID) throws Exception {
        PreparedStatement ps;
        if (containerID == null) {
            ps = connection.prepareStatement("SELECT id, name, creationdate, state, queueid FROM Containers");
        } else {
            ps = connection.prepareStatement("SELECT id, name, creationdate, state, queueid FROM Containers WHERE id = " + containerID);
        }
        return ps;
    }

    PreparedStatement GetQueues(Connection connection, String containerID) throws Exception {
        PreparedStatement ps;
        if (containerID == null) {
            ps = connection.prepareStatement("SELECT id, hostname, queuename, queuepass FROM Queues");
        } else {
            ps = connection.prepareStatement("SELECT id, hostname, queuename, queuepass FROM Queues WHERE id = " + containerID);
        }
        return ps;
    }

    ResultSet GetResultSet(PreparedStatement ps) throws Exception {
        return ps.executeQuery();
    }
}

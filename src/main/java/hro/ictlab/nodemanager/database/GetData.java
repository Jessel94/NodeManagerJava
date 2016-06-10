package hro.ictlab.nodemanager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class GetData {

    PreparedStatement getContainers(Connection connection, String containerId) throws Exception {
        PreparedStatement ps;
        if (containerId == null) {
            ps = connection.prepareStatement("SELECT id, name, creationdate, state, queueid FROM Containers");
        } else {
            ps = connection.prepareStatement("SELECT id, name, creationdate, state, queueid FROM Containers WHERE id = " + containerId);
        }
        return ps;
    }

    PreparedStatement getNodes(Connection connection, String nodeID) throws Exception {
        PreparedStatement ps;
        if (nodeID == null) {
            ps = connection.prepareStatement("SELECT id, name, queueid, lastchecked FROM Nodes");
        } else {
            ps = connection.prepareStatement("SELECT id, name, queueid, lastchecked FROM Nodes WHERE id = " + nodeID);
        }
        return ps;
    }

    ResultSet getResultSet(PreparedStatement ps) throws Exception {
        return ps.executeQuery();
    }
}

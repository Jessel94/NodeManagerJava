package hro.ictlab.nodemanager.database.data;

import java.sql.Connection;
import java.sql.PreparedStatement;

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

    PreparedStatement getNodes(Connection connection, String nodeId, String nodeIp) throws Exception {
        PreparedStatement ps;
        if (nodeId != null) {
            ps = connection.prepareStatement("SELECT id, name, queueid, lastchecked FROM Nodes WHERE id = " + nodeId);
        } else if (nodeIp != null) {
            ps = connection.prepareStatement("SELECT id, name, queueid, lastchecked FROM Nodes WHERE name = '" + nodeIp + "'");
        } else {
            ps = connection.prepareStatement("SELECT id, name, queueid, lastchecked FROM Nodes");
        }
        return ps;
    }
}

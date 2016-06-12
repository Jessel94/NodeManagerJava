package hro.ictlab.nodemanager.database.data;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Class used to setup a PreparedStatement to allow pulling data from the database.
 */
class GetData {

    /**
     * Method used to send a delete command to a specific container
     *
     * @param connection  The connection holds the information needed to connect to the database
     * @param containerId The id of the container you would like to get the data from, can be NULL
     * @return Returns a PreparedStatement to which can be used to get dat from the database.
     */
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

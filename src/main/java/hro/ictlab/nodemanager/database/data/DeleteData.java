package hro.ictlab.nodemanager.database.data;

import java.sql.Connection;
import java.sql.PreparedStatement;

class DeleteData {

    PreparedStatement deleteContainer(Connection connection, String containerId) throws Exception {
        return connection.prepareStatement(
                "DELETE FROM Containers WHERE id = " + containerId
        );
    }

    PreparedStatement deleteNode(Connection connection, String containerId) throws Exception {
        return connection.prepareStatement(
                "DELETE FROM Nodes WHERE name = '" + containerId + "'"
        );
    }

    PreparedStatement deleteQueue(Connection connection, String queueId) throws Exception {
        return connection.prepareStatement(
                "DELETE FROM Queues WHERE id = " + queueId
        );
    }
}

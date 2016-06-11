package hro.ictlab.nodemanager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

class DeleteData {
    PreparedStatement deleteContainer(Connection connection, String containerId) throws Exception {
        return connection.prepareStatement(
                "DELETE FROM Containers WHERE id = " + containerId
        );
    }

    void executeStatement(PreparedStatement ps) throws Exception {
        ps.execute();
    }

}

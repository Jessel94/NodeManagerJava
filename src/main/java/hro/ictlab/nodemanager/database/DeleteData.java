package hro.ictlab.nodemanager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

class DeleteData {
    PreparedStatement deleteContainer(Connection connection, String containerId) throws Exception {
        PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM Containers WHERE id = " + containerId
        );
        return ps;
    }

    PreparedStatement executeStatement(PreparedStatement ps) throws Exception {
        ps.execute();
        return ps;
    }

}

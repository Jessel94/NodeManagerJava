package hro.ictlab.nodemanager.database;

import java.sql.Connection;
import java.sql.Statement;

class UpdateData {

    String containerStatus(String containerID, String newStatus) throws Exception {
        return ("UPDATE Containers SET state='" + newStatus + "' WHERE id = " + containerID);
    }

    String nodeStatus(String queueid) throws Exception {
        return ("UPDATE Nodes SET lastchecked = NULL WHERE queueid = " + queueid);
    }

    Statement getStatement(Connection connection) throws Exception {
        return connection.createStatement();
    }

    void executeUpdate(Statement statement, String sql) throws Exception {
        statement.executeUpdate(sql);
    }
}


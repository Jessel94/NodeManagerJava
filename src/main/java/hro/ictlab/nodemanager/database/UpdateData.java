package hro.ictlab.nodemanager.database;

import java.sql.Connection;
import java.sql.Statement;

class UpdateData {

    String ContainerStatus(String containerID, String newStatus) throws Exception {
        return ("UPDATE Containers SET state='" + newStatus + "' WHERE id = " + containerID);
    }

    Statement GetStatement(Connection connection) throws Exception {
        return connection.createStatement();
    }

    void ExecuteUpdate(Statement statement, String sql) throws Exception {
        statement.executeUpdate(sql);
    }
}


package hro.ictlab.nodemanager.database;

import hro.ictlab.nodemanager.models.DockerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseHandler {

    private final GetData getData = new GetData();
    private final UpdateData updateData = new UpdateData();
    private final DataFormatter dataFormatter = new DataFormatter();
    private final InsertData insertData = new InsertData();
    private final MessageBuilder messageBuilder = new MessageBuilder();


    public String containerRequest(String containerId, Connection conn) throws Exception {
        String result = "No Data";
        PreparedStatement ps = getData.getContainers(conn, containerId);
        ResultSet rs = getData.getResultSet(ps);
        if (isResultSetNotEmpty(rs)) {
            ArrayList messageData = dataFormatter.containerFormatter(rs);
            result = dataFormatter.gsonFormatter(messageData);
        }
        return result;
    }

    public String nodeRequest(String nodeId, Connection conn) throws Exception {
        String result = "No Data";
        PreparedStatement ps = getData.getNodes(conn, nodeId);
        ResultSet rs = getData.getResultSet(ps);
        if (isResultSetNotEmpty(rs)) {
            ArrayList messageData = dataFormatter.nodeFormatter(rs);
            result = dataFormatter.gsonFormatter(messageData);
        }
        return result;
    }

    public String containerQueueID(String containerId, Connection conn) throws Exception {
        PreparedStatement ps = getData.getContainers(conn, containerId);
        ResultSet rs = getData.getResultSet(ps);
        return Integer.toString(dataFormatter.containerData(rs).getQueueid());
    }

    public void updateContainer(String containerId, String command, Connection conn, boolean customStatus) throws Exception {
        Statement statement = updateData.getStatement(conn);
        String newStatus = null;
        if (!customStatus) {
            newStatus = messageBuilder.generateMessage(command);
        } else {
            newStatus = command;
        }
        String sql = updateData.containerStatus(containerId, newStatus);
        updateData.executeUpdate(statement, sql);
    }

    public void updateContainerList(ArrayList<DockerData> dockerDatas, Connection conn) throws Exception {
        for (DockerData dockerData : dockerDatas) {
            String containerId = dockerData.getContainerID();
            String newStatus = dockerData.getStatus();
            updateContainer(containerId, newStatus, conn, true);
        }
    }

    public void updateNode(String queueId, Connection conn) throws Exception {
        Statement statement = updateData.getStatement(conn);
        String sql = updateData.nodeStatus(queueId);
        updateData.executeUpdate(statement, sql);
    }

    public int newQueue(String hostName, String userName, String passWord, String ip, Connection conn) throws Exception {
        PreparedStatement ps = insertData.newQueue(conn, hostName, userName, passWord);
        ResultSet rs = insertData.getResultSet(ps);
        int queueID = dataFormatter.queueData(rs).getId();
        ps = insertData.newNode(conn, ip, queueID);
        insertData.executeStatement(ps);
        return queueID;
    }

    private boolean isResultSetNotEmpty(ResultSet rs) throws Exception {
        if (rs.next()) {
            rs.beforeFirst();
            return true;
        } else {
            return false;
        }
    }

    public boolean isCommandValid(String command) {
        return command.equals("start") || command.equals("stop") || command.equals("restart");
    }

    //Currently not used
    /*
    public String nodeQueueID(String id) throws Exception {
        Connection conn = null;
        String messageData = null;
        try {
            conn = connector.getConnection();
            PreparedStatement ps = getData.getNodes(conn, id);
            ResultSet rs = getData.getResultSet(ps);
            messageData = Integer.toString(dataFormatter.nodeData(rs).getQueueid());
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try {
                connector.closeConnection(conn);
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return messageData;
    }
    */
}

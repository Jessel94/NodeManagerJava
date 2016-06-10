package hro.ictlab.nodemanager.database;

import hro.ictlab.nodemanager.models.DockerData;
import hro.ictlab.nodemanager.models.Node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DbHandler {

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
        PreparedStatement ps = getData.getNodes(conn, nodeId, null);
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

    public String nodeQueueID(String nodeIp, Connection conn) throws Exception {
        PreparedStatement ps = getData.getNodes(conn, null, nodeIp);
        ResultSet rs = getData.getResultSet(ps);
        return Integer.toString(dataFormatter.nodeData(rs).getQueueid());
    }

    public ArrayList<Node> nodeList(Connection conn) throws Exception {
        ArrayList<Node> result = null;
        PreparedStatement ps = getData.getNodes(conn, null, null);
        ResultSet rs = getData.getResultSet(ps);
        if (isResultSetNotEmpty(rs)) {
            result = dataFormatter.nodeFormatter(rs);
        }
        return result;
    }

    public void updateContainer(String containerId, String command, Connection conn, boolean customStatus) throws Exception {
        Statement statement = updateData.getStatement(conn);
        String newStatus;
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
        int queueID = dataFormatter.queueDataSql(rs).getId();
        ps = insertData.newNode(conn, ip, queueID);
        insertData.executeStatement(ps);
        return queueID;
    }

    public String newContainer(String name, String state, String queueId, Connection conn) throws Exception {
        PreparedStatement ps = insertData.newContainer(conn, name, state, queueId);
        ResultSet rs = insertData.getResultSet(ps);
        return Integer.toString(dataFormatter.containerDataSql(rs).getId());
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
}

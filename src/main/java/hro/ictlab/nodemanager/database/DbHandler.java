package hro.ictlab.nodemanager.database;

import hro.ictlab.nodemanager.database.data.DataFormatter;
import hro.ictlab.nodemanager.database.data.DataHandler;
import hro.ictlab.nodemanager.models.DockerData;
import hro.ictlab.nodemanager.models.Node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DbHandler {

    private final DataFormatter dataFormatter = new DataFormatter();
    private final DataHandler dataHandler = new DataHandler();


    public String containerRequest(String containerId, Connection conn) throws Exception {
        String result = "No Data";
        PreparedStatement ps = dataHandler.getContainers(conn, containerId);
        ResultSet rs = dataHandler.getResultSet(ps);
        if (isResultSetNotEmpty(rs)) {
            ArrayList messageData = dataFormatter.containerFormatter(rs);
            result = dataFormatter.gsonFormatter(messageData);
        }
        return result;
    }

    public String nodeRequest(String nodeId, Connection conn) throws Exception {
        String result = "No Data";
        PreparedStatement ps = dataHandler.getNodes(conn, nodeId, null);
        ResultSet rs = dataHandler.getResultSet(ps);
        if (isResultSetNotEmpty(rs)) {
            ArrayList messageData = dataFormatter.nodeFormatter(rs);
            result = dataFormatter.gsonFormatter(messageData);
        }
        return result;
    }

    public String containerQueueID(String containerId, Connection conn) throws Exception {
        PreparedStatement ps = dataHandler.getContainers(conn, containerId);
        ResultSet rs = dataHandler.getResultSet(ps);
        return Integer.toString(dataFormatter.containerData(rs).getQueueId());
    }

    public String containerName(String containerId, Connection conn) throws Exception {
        PreparedStatement ps = dataHandler.getContainers(conn, containerId);
        ResultSet rs = dataHandler.getResultSet(ps);
        return dataFormatter.containerData(rs).getName();
    }

    public String nodeQueueID(String nodeIp, Connection conn) throws Exception {
        PreparedStatement ps = dataHandler.getNodes(conn, null, nodeIp);
        ResultSet rs = dataHandler.getResultSet(ps);
        return Integer.toString(dataFormatter.nodeData(rs).getQueueId());
    }

    public ArrayList<Node> nodeList(Connection conn) throws Exception {
        ArrayList<Node> result = null;
        PreparedStatement ps = dataHandler.getNodes(conn, null, null);
        ResultSet rs = dataHandler.getResultSet(ps);
        if (isResultSetNotEmpty(rs)) {
            result = dataFormatter.nodeFormatter(rs);
        }
        return result;
    }

    public void updateContainer(String containerId, String command, Connection conn, boolean customStatus) throws Exception {
        Statement statement = dataHandler.getStatement(conn);
        String newStatus;
        if (!customStatus) {
            newStatus = MessageBuilder.generateMessage(command);
        } else {
            newStatus = command;
        }
        String sql = dataHandler.containerStatus(containerId, newStatus);
        dataHandler.executeUpdate(statement, sql);
    }

    public void updateContainerCrash(String containerId, Connection conn) throws Exception {
        Statement statement = dataHandler.getStatement(conn);
        String sql = dataHandler.containerStatusCrash(containerId);
        dataHandler.executeUpdate(statement, sql);
    }

    public void updateContainerList(ArrayList<DockerData> dockerDatas, Connection conn) throws Exception {
        for (DockerData dockerData : dockerDatas) {
            String containerId = dockerData.getContainerID();
            String newStatus = dockerData.getStatus();
            updateContainer(containerId, newStatus, conn, true);
        }
    }

    public void updateNode(String queueId, Connection conn) throws Exception {
        Statement statement = dataHandler.getStatement(conn);
        String sql = dataHandler.nodeStatus(queueId);
        dataHandler.executeUpdate(statement, sql);
    }

    public int newQueue(String hostName, String userName, String passWord, String ip, Connection conn) throws Exception {
        PreparedStatement ps = dataHandler.newQueue(conn, hostName, userName, passWord);
        ResultSet rs = dataHandler.insertResultSet(ps);
        int queueID = dataFormatter.queueDataSql(rs).getId();
        ps = dataHandler.newNode(conn, ip, queueID);
        dataHandler.executeStatement(ps);
        return queueID;
    }

    public String newContainer(String name, String queueId, Connection conn) throws Exception {
        PreparedStatement ps = dataHandler.newContainer(conn, name, "Starting", queueId);
        ResultSet rs = dataHandler.insertResultSet(ps);
        return Integer.toString(dataFormatter.containerDataSql(rs).getId());
    }

    public void deleteContainer(String containerId, Connection conn) throws Exception {
        PreparedStatement ps = dataHandler.deleteContainer(conn, containerId);
        dataHandler.executeStatement(ps);
    }

    public void deleteNode(String nodeIp, Connection conn) throws Exception {
        PreparedStatement ps = dataHandler.deleteNode(conn, nodeIp);
        dataHandler.executeStatement(ps);
    }

    public void deleteQueue(String queueId, Connection conn) throws Exception {
        PreparedStatement ps = dataHandler.deleteQueue(conn, queueId);
        dataHandler.executeStatement(ps);
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

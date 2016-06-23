package hro.ictlab.nodemanager.database.data;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataHandler {

    private final GetData getData = new GetData();
    private final UpdateData updateData = new UpdateData();
    private final InsertData insertData = new InsertData();
    private final DeleteData deleteData = new DeleteData();


    public PreparedStatement getContainers(Connection connection, String containerId) throws Exception {
        return getData.getContainers(connection, containerId);
    }

    public PreparedStatement getNodes(Connection connection, String nodeId, String nodeIp) throws Exception {
        return getData.getNodes(connection, nodeId, nodeIp);
    }

    public PreparedStatement getQueue(Connection connection, String queueId) throws Exception {
        return getData.getQueues(connection, queueId);
    }

    public ResultSet getResultSet(PreparedStatement ps) throws Exception {
        return ps.executeQuery();
    }

    public String containerStatus(String containerId, String newStatus) {
        return updateData.containerStatus(containerId, newStatus);
    }

    public String nodeStatus(String queueId) {
        return updateData.nodeStatus(queueId);
    }

    public Statement getStatement(Connection connection) throws Exception {
        return connection.createStatement();
    }

    public void executeUpdate(Statement statement, String sql) throws Exception {
        statement.executeUpdate(sql);
    }

    public PreparedStatement newContainer(Connection connection, String name, String state, String queueId) throws Exception {
        return insertData.newContainer(connection, name, state, queueId);
    }

    public PreparedStatement newQueue(Connection connection, String hostName, String userName, String passWord) throws Exception {
        return insertData.newQueue(connection, hostName, userName, passWord);
    }

    public PreparedStatement newNode(Connection connection, String ip, int queueID) throws Exception {
        return insertData.newNode(connection, ip, queueID);
    }

    public ResultSet insertResultSet(PreparedStatement ps) throws Exception {
        ps = executeStatement(ps);
        return ps.getGeneratedKeys();
    }

    public PreparedStatement executeStatement(PreparedStatement ps) throws Exception {
        ps.execute();
        return ps;
    }

    public PreparedStatement deleteContainer(Connection connection, String containerId) throws Exception {
        return deleteData.deleteContainer(connection, containerId);
    }

    public PreparedStatement deleteNode(Connection connection, String nodeIp) throws Exception {
        return deleteData.deleteNode(connection, nodeIp);
    }

    public PreparedStatement deleteQueue(Connection connection, String queueId) throws Exception {
        return deleteData.deleteQueue(connection, queueId);
    }

    public String containerStatusCrash(String queueId) {
        return updateData.containerStatusCrash(queueId);
    }
}

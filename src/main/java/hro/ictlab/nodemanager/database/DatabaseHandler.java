package hro.ictlab.nodemanager.database;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    private Connector connector = new Connector();
    private GetData getData = new GetData();
    private UpdateData updateData = new UpdateData();
    private DataFormatter dataFormatter = new DataFormatter();
    private InsertData insertData = new InsertData();


    public String containerRequest(String id) throws Exception {
        Connection conn = null;
        String message = "No Data";
        try {
            conn = connector.getConnection();
            PreparedStatement ps = getData.getContainers(conn, id);
            ResultSet rs = getData.getResultSet(ps);
            ArrayList messageData = dataFormatter.containerFormatter(rs);
            message = dataFormatter.gsonFormatter(messageData);
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
        return message;
    }

    public String nodeRequest(String id) throws Exception {
        Connection conn = null;
        String message = "No Data";
        try {
            conn = connector.getConnection();
            PreparedStatement ps = getData.getNodes(conn, id);
            ResultSet rs = getData.getResultSet(ps);
            ArrayList messageData = dataFormatter.nodeFormatter(rs);
            message = dataFormatter.gsonFormatter(messageData);
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
        return message;
    }

    public String queueRequest(String id) throws Exception {
        Connection conn = null;
        String message = "No Data";
        try {
            conn = connector.getConnection();
            PreparedStatement ps = getData.getQueues(conn, id);
            ResultSet rs = getData.getResultSet(ps);
            ArrayList messageData = dataFormatter.queueFormatter(rs);
            message = dataFormatter.gsonFormatter(messageData);
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
        return message;
    }

    public String containerQueueID(String id) throws Exception {
        Connection conn = null;
        String messageData = null;
        try {
            conn = connector.getConnection();
            PreparedStatement ps = getData.getContainers(conn, id);
            ResultSet rs = getData.getResultSet(ps);
            messageData = Integer.toString(dataFormatter.containerData(rs).getQueueid());
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

    public void updateContainer(String id, String newStatus) throws Exception {
        Connection conn = null;
        try {
            conn = connector.getConnection();
            Statement statement = updateData.getStatement(conn);
            String sql = updateData.containerStatus(id, newStatus);
            updateData.executeUpdate(statement, sql);
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
    }

    public int newQueue(String hostName, String userName, String passWord) throws Exception {
        Connection conn = null;
        int queueID = 0;
        try {
            conn = connector.getConnection();
            PreparedStatement ps = insertData.newQueue(conn, hostName, userName, passWord);
            ResultSet rs = insertData.getResultSet(ps);
            queueID = dataFormatter.queueData(rs).getId();
            String nodeName = "Node" + Integer.toString(queueID);
            ps = insertData.newNode(conn, nodeName, queueID);
            insertData.executeStatement(ps);
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
        return queueID;
    }
}

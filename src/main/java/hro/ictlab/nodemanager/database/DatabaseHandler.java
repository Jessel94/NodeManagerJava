package hro.ictlab.nodemanager.database;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    private Connector connector = new Connector();
    private GetData getData = new GetData();
    private UpdateData updateData = new UpdateData();
    private DataFormatter dataFormatter = new DataFormatter();
    private InsertData insertData = new InsertData();


    public String ContainerRequest(String id) throws Exception {
        Connection conn = null;
        String message = "No Data";
        try {
            conn = connector.GetConnection();
            PreparedStatement ps = getData.GetContainers(conn, id);
            ResultSet rs = getData.GetResultSet(ps);
            ArrayList messageData = dataFormatter.ContainerFormatter(rs);
            message = dataFormatter.GSONFormatter(messageData);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try{
                connector.CloseConnection(conn);
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return message;
    }

    public String QueueRequest(String id) throws Exception {
        Connection conn = null;
        String message = "No Data";
        try {
            conn = connector.GetConnection();
            PreparedStatement ps = getData.GetQueues(conn, id);
            ResultSet rs = getData.GetResultSet(ps);
            ArrayList messageData = dataFormatter.QueueFormatter(rs);
            message = dataFormatter.GSONFormatter(messageData);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try{
                connector.CloseConnection(conn);
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return message;
    }

    public ArrayList QueueData(String id) throws Exception {
        Connection conn = null;
        ArrayList messageData = new ArrayList();
        try {
            conn = connector.GetConnection();
            PreparedStatement ps = getData.GetQueues(conn, id);
            ResultSet rs = getData.GetResultSet(ps);
            messageData = dataFormatter.ContainerFormatter(rs);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try{
                connector.CloseConnection(conn);
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return messageData;
    }

    public void UpdateContainer(String id, String newStatus) throws Exception {
        Connection conn = null;
        try {
            conn = connector.GetConnection();
            Statement statement = updateData.GetStatement(conn);
            String sql = updateData.ContainerStatus(id, newStatus);
            updateData.ExecuteUpdate(statement, sql);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try {
                connector.CloseConnection(conn);
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public int NewQueue() throws Exception {
        Connection conn = null;
        int queueID = 0;
        try {
            conn = connector.GetConnection();
            PreparedStatement ps = insertData.NewQueue2(conn);
            ps.execute();
            queueID = 1;
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try {
                connector.CloseConnection(conn);
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return queueID;
    }
}

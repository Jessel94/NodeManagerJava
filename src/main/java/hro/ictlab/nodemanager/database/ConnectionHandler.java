package hro.ictlab.nodemanager.database;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionHandler {

    private Connector connector = new Connector();
    private GetData getData = new GetData();
    private DataFormatter dataFormatter = new DataFormatter();


    public String ContainerRequest(String id) throws Exception {
        Connection conn = null;
        String message = "No Data";
        try {
            conn = connector.GetConnection();
            ResultSet rs = getData.GetContainers(conn, id);
            ArrayList messageData = dataFormatter.ContainerFormatter(rs);
            Gson gson = new Gson();
            message = gson.toJson(messageData).toString();
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
            ResultSet rs = getData.GetQueues(conn, id);
            ArrayList messageData = dataFormatter.QueueFormatter(rs);
            Gson gson = new Gson();
            message = gson.toJson(messageData).toString();
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
            ResultSet rs = getData.GetQueues(conn, id);
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
}
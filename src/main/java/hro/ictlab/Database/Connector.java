package main.java.hro.ictlab.Database;

import main.java.hro.ictlab.models.Container;

import java.sql.*;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Connector {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://145.24.222.223/NodeManager";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "100ddJ";

    public String main() {
        Connection conn = null;
        Statement stmt = null;
        ArrayList messagesData = null;
        String messages = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Get data
            messagesData = GetMessages(conn);
            Gson gson = new Gson();
            messages = gson.toJson(messagesData).toString();


        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        return messages;
    }//end main

    private ArrayList GetMessages(Connection connection) throws Exception
    {
        ArrayList messageData = new ArrayList();
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT id, name, creationdate, state, queueid FROM Containers");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Container container = new Container();
                container.setId(rs.getInt("id"));
                container.setId(rs.getInt("queueid"));
                container.setName(rs.getString("name"));
                container.setName(rs.getString("creationdate"));
                container.setName(rs.getString("state"));
                messageData.add(container);
            }
            rs.close();
            return messageData;
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}

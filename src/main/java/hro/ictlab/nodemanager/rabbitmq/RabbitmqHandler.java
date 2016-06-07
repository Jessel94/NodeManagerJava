package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import hro.ictlab.nodemanager.database.DatabaseHandler;

public class RabbitmqHandler {

    private Connector connector = new Connector();
    private Send send = new Send();
    private DatabaseHandler databaseHandler = new DatabaseHandler();

    public String ProcessCommand(String containerID, String message) throws Exception {
        Connection conn = null;
        Channel channel = null;
        String result = "no data";
        try{
            conn = connector.GetConnection();
            channel = connector.GetChannel(conn);
            if (message.equals("start") || message.equals("stop") || message.equals("restart")) {
                result = send.StartStopRestart(containerID, message, channel);
                if (message.equals("start")) {
                    databaseHandler.UpdateContainer(containerID, "Started");
                }
                if (message.equals("stop")) {
                    databaseHandler.UpdateContainer(containerID, "Stopping");
                }
                if (message.equals("restart")) {
                    databaseHandler.UpdateContainer(containerID, "Restarting");
                }
            }
        }
        catch (Exception e){
            throw e;
        }
        finally {
            try{
                connector.CloseChannel(channel);
                connector.CloseConnection(conn);
            }
            catch (Exception e){
                throw e;
            }
        }
        return result;
    }

    public String RequestQueue() throws Exception {
        Connection conn = null;
        Channel channel = null;
        String result = "no data";
        try{
            conn = connector.GetConnection();
            channel = connector.GetChannel(conn);
            int test = databaseHandler.NewQueue();
            result = String.valueOf(test);
        }
        catch (Exception e){
            throw e;
        }
        finally {
            try{
                connector.CloseChannel(channel);
                connector.CloseConnection(conn);
            }
            catch (Exception e){
                throw e;
            }
        }
        return result;
    }
}

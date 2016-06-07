package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import hro.ictlab.nodemanager.database.DatabaseHandler;

public class RabbitmqHandler {

    private Connector connector = new Connector();
    private Send send = new Send();
    private Queue queue = new Queue();
    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private MessageBuilder messageBuilder = new MessageBuilder();

    public String ProcessCommand(String containerID, String message) throws Exception {
        Connection conn = null;
        Channel channel = null;
        String result = "no data";
        try{
            conn = connector.GetConnection();
            channel = connector.GetChannel(conn);
            if (message.equals("start") || message.equals("stop") || message.equals("restart")) {
                String queueId = databaseHandler.ContainerData(containerID);
                result = send.StartStopRestart(containerID, queueId, message, channel);
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
            int queueID = databaseHandler.NewQueue();
            result = queue.NewQueue(channel, queueID);
            String userName = "test";
            String passWord = "test";
            result = messageBuilder.main(result, "setid", userName, passWord);
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

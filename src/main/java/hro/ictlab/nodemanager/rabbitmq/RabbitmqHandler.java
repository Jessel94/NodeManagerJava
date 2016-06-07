package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import hro.ictlab.nodemanager.database.DatabaseHandler;
import hro.ictlab.nodemanager.services.RabbitMQ;

public class RabbitmqHandler {

    private Connector connector = new Connector();
    private Send send = new Send();
    private Queue queue = new Queue();
    private Generator generator = new Generator();
    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private MessageBuilder messageBuilder = new MessageBuilder();
    private RabbitMQ rabbitMQ = new RabbitMQ();

    public String processCommand(String containerID, String message) throws Exception {
        Connection conn = null;
        Channel channel = null;
        String result = "no data";
        try {
            conn = connector.getConnection();
            channel = connector.getChannel(conn);
            if (message.equals("start") || message.equals("stop") || message.equals("restart")) {
                String queueId = databaseHandler.containerQueueID(containerID);
                result = send.startStopRestart(containerID, queueId, message, channel);
                if (message.equals("start")) {
                    databaseHandler.updateContainer(containerID, "Started");
                }
                if (message.equals("stop")) {
                    databaseHandler.updateContainer(containerID, "Stopping");
                }
                if (message.equals("restart")) {
                    databaseHandler.updateContainer(containerID, "Restarting");
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                connector.closeChannel(channel);
                connector.closeConnection(conn);
            } catch (Exception e) {
                throw e;
            }
        }
        return result;
    }

    public String requestQueue() throws Exception {
        Connection conn = null;
        Channel channel = null;
        String result = "no data";
        try {
            conn = connector.getConnection();
            channel = connector.getChannel(conn);
            String hostName = System.getenv("RABBITMQ");
            String userName = generator.generateUser();
            String passWord = generator.generatePass();
            rabbitMQ.createUser(userName, passWord);
            int queueID = databaseHandler.newQueue(hostName, userName, passWord);
            result = queue.newQueue(channel, queueID);
            result = messageBuilder.main(result, "setid", userName, passWord);
        } catch (Exception e) {
            result = e.toString();
        } finally {
            try {
                connector.closeChannel(channel);
                connector.closeConnection(conn);
            } catch (Exception e) {
                result = e.toString();
            }
        }
        return result;
    }
}

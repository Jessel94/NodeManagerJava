package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private Connector connector = new Connector();
    private MessageBuilder messageBuilder = new MessageBuilder();

    public String main(String containerID, String message) throws Exception {
        Connection conn = null;
        Channel channel = null;
        String result = "no data";
        try{
            conn = connector.GetConnection();
            channel = connector.GetChannel(conn);
            if (message.equals("start") || message.equals("stop")) {
                result = StartStop(containerID, message, channel);
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

    private String StartStop (String containerID, String message, Channel channel) throws Exception {

        //temporary solution to get queueID
        String queueID = containerID;

        //temporary solution to get message body
        String messageArray = messageBuilder.main(containerID, message);

        channel.queueDeclare(queueID, false, false, false, null);
        channel.basicPublish("", queueID, null, messageArray.getBytes("UTF-8"));
        return "succes";
    }
}
package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private Connector connector = new Connector();

    public String main(String containerID, String message){
        Connection conn = null;
        Channel channel = null;
        String result = "no data";
        try{
            conn = connector.GetConnection();
            channel = connector.GetChannel(conn);
            if(message.equals("Start") || message.equals("Stop")){
                result = StartStop(containerID, message, channel);
            }
        }
        catch (Exception e){
            result = "failed";
            return result;
        }
        finally {
            try{
                connector.CloseChannel(channel);
                connector.CloseConnection(conn);
            }
            catch (Exception e){
                result = "failed";
                return result;
            }
        }
        return result;
    }

    private String StartStop (String containerID, String message, Channel channel) throws Exception {

        //temporary solution
        String queueID = containerID;

        channel.queueDeclare(queueID, false, false, false, null);
        channel.basicPublish("", queueID, null, message.getBytes("UTF-8"));
        return "succes";
    }
}
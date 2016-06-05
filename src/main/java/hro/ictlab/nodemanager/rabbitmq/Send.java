package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

    public static String main(String queueID, String message) throws Exception {
        try {
            ConnectionFactory factory = new ConnectionFactory();

            String envVar = System.getenv("RABBITMQ");
            if(envVar == null) { envVar = "localhost"; }
            factory.setHost(envVar);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(queueID, false, false, false, null);
            channel.basicPublish("", queueID, null, message.getBytes("UTF-8"));

            channel.close();
            connection.close();

            return new String("succes");
        }
        catch (Exception e){
            return new String("failed");
        }
    }
}
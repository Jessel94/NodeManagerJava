package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Class that is used to handle all methods relating to rabbitMQ
 */
public class RabbitHandler {

    private final Send send = new Send();
    private final Queue queue = new Queue();
    private final MessageBuilder messageBuilder = new MessageBuilder();
    private final RabbitGenerator rabbitGenerator = new RabbitGenerator();
    private final RabbitApi rabbitApi = new RabbitApi();

    /**
     * Method processes and sends the command to the specified Queue
     */
    public void processCommand(String containerID, String queueId, String message, Channel channel, String nodePort, String containerPort, String image) throws Exception {
        String containerId = "container" + containerID;
        String messageArray = messageBuilder.buildMessage(containerId, message, null, null, nodePort, containerPort, image);
        send.sendMessage(queueId, messageArray, channel);
    }

    /**
     * Method processes and sends the command to the specified Queue
     */
    public void processExport(String containerID, String queueId, String message, Channel channel, String queuename, String queuepass) throws Exception {
        String containerId = "container" + containerID;
        String messageArray = messageBuilder.buildExport(containerId, queueId, message, queuename, queuepass);
        send.sendMessage(queueId, messageArray, channel);
    }

    /**
     * Method processes and sends the command to the specified Queue
     */
    public String requestQueue(int queueID, String userName, String passWord, Channel channel) throws Exception {
        String result = queue.newQueue(channel, queueID);
        return messageBuilder.buildMessage(result, "setid", userName, passWord, null, null, null);
    }

    public String generateUserName() {
        return rabbitGenerator.generateUser();
    }

    public String generatePassWord() {
        return rabbitGenerator.generatePass();
    }

    /**
     * Method to create a new user in rabbitMQ
     */
    public void createUser(String userName, String passWord, CloseableHttpClient client) throws Exception {
        rabbitApi.createUser(userName, passWord, client);
        rabbitApi.userPermissions(userName, client);
    }
}

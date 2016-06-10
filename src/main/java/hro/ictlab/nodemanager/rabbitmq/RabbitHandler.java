package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;
import org.apache.http.impl.client.CloseableHttpClient;

public class RabbitHandler {

    private final Send send = new Send();
    private final Queue queue = new Queue();
    private final MessageBuilder messageBuilder = new MessageBuilder();
    private final RabbitGenerator rabbitGenerator = new RabbitGenerator();
    private final RabbitApi rabbitApi = new RabbitApi();

    public String processCommand(String containerID, String queueId, String message, Channel channel) throws Exception {
        String containerId = "container" + containerID;
        String messageArray = messageBuilder.main(containerId, message, null, null);
        return send.startStopRestart(queueId, messageArray, channel);
    }

    public String requestQueue(int queueID, String userName, String passWord, Channel channel) throws Exception {
        String result = queue.newQueue(channel, queueID);
        return messageBuilder.main(result, "setid", userName, passWord);
    }

    public String generateUserName() {
        return rabbitGenerator.generateUser();
    }

    public String generatePassWord() {
        return rabbitGenerator.generatePass();
    }

    public void createUser(String userName, String passWord, CloseableHttpClient client) throws Exception {
        rabbitApi.createUser(userName, passWord, client);
        rabbitApi.userPermissions(userName, client);
    }
}

package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;

public class RabbitmqHandler {

    private final Send send = new Send();
    private final Queue queue = new Queue();
    private final MessageBuilder messageBuilder = new MessageBuilder();

    public String processCommand(String containerID, String queueId, String message, Channel channel) throws Exception {
        String messageArray = messageBuilder.main(containerID, message, null, null);
        return send.startStopRestart(queueId, messageArray, channel);
    }

    public String requestQueue(int queueID, String userName, String passWord, Channel channel) throws Exception {
        String result = queue.newQueue(channel, queueID);
        return messageBuilder.main(result, "setid", userName, passWord);
    }
}

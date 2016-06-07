package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;

class Send {

    private MessageBuilder messageBuilder = new MessageBuilder();

    String startStopRestart(String containerID, String queueID, String message, Channel channel) throws Exception {

        String messageArray = messageBuilder.main(containerID, message, null, null);

        channel.queueDeclare(queueID, true, false, false, null);
        channel.basicPublish("", queueID, null, messageArray.getBytes("UTF-8"));
        return "succes";
    }
}
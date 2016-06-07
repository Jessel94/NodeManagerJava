package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;

class Send {

    private MessageBuilder messageBuilder = new MessageBuilder();

    String StartStopRestart(String containerID, String message, Channel channel) throws Exception {

        //temporary solution to get queueID
        String queueID = containerID;

        //temporary solution to get message body
        String messageArray = messageBuilder.main(containerID, message);

        channel.queueDeclare(queueID, false, false, false, null);
        channel.basicPublish("", queueID, null, messageArray.getBytes("UTF-8"));
        return "succes";
    }
}
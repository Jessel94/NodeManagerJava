package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;

class Send {

    String startStopRestart(String queueID, String message, Channel channel) throws Exception {
        channel.queueDeclare(queueID, true, false, false, null);
        channel.basicPublish("", queueID, null, message.getBytes("UTF-8"));
        return "Message has been pushed into the Queue.";
    }
}
package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;

class Send {

    void sendMessage(String queueID, String message, Channel channel) throws Exception {
        channel.queueDeclare(queueID, true, false, false, null);
        channel.basicPublish("", queueID, null, message.getBytes("UTF-8"));
    }
}
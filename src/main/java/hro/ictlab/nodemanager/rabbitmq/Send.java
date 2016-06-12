package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;

/**
 * This class handles the sending of the messages into the RabbitMQ queue.
 */
class Send {

    /**
     * Method used to send a message into rabbitMQ to the correct node/container
     *
     * @param queueId The queueId is the id of the queue you want to push the message to
     *                so that the correct node/containers receives the message
     * @param message The message is the formated message you want to send over rabbitMQ
     * @param channel The channel holds the login information for rabbitMQ so that the
     *                message is actually pushed into the queue.
     * @throws Exception
     */
    void sendMessage(String queueId, String message, Channel channel) throws Exception {
        channel.queueDeclare(queueId, true, false, false, null);
        channel.basicPublish("", queueId, null, message.getBytes("UTF-8"));
    }
}

package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;

class Queue {

    String newQueue(Channel channel, int queue) throws Exception {
        String queueID = Integer.toString(queue);
        channel.queueDeclare(queueID, true, false, false, null);
        return queueID;
    }
}

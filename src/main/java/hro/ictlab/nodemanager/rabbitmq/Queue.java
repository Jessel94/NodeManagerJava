package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;

class Queue {

    String NewQueue(Channel channel) throws Exception {

        //channel.queueDeclare(queueID, true, false, false, null);
        //return queueID;
        return "temp";
    }
}

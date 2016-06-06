package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

class Connector {

    Connection GetConnection () throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        String envVar = System.getenv("RABBITMQ");
        if(envVar == null) { envVar = "localhost"; }
        factory.setHost(envVar);

        return factory.newConnection();
    }

    void CloseConnection (Connection connection) throws Exception {
        connection.close();
    }

    Channel GetChannel (Connection connection) throws Exception {
        return connection.createChannel();
    }

    void CloseChannel (Channel channel) throws Exception {
        channel.close();
    }
}

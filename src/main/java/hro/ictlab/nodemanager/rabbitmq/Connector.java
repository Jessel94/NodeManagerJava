package hro.ictlab.nodemanager.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

class Connector {

    Connection GetConnection () throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(System.getenv("RABBITMQ"));
        factory.setUsername(System.getenv("RABBITMQ_USER"));
        factory.setPassword(System.getenv("RABBITMQ_PASS"));

        return factory.newConnection();
    }

    void CloseConnection (Connection connection) throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    Channel GetChannel (Connection connection) throws Exception {
        return connection.createChannel();
    }

    void CloseChannel (Channel channel) throws Exception {
        if (channel != null) {
            channel.close();
        }
    }
}

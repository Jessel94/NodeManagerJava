package hro.ictlab.nodemanager.connectors;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * This class is used to setup and close all connections for the RabbitMQ.
 */
public class RabbitConnector {

    /**
     * Method used to create a connecetion that can be used to connect to rabbitMQ
     *
     * @return Returns a connection that can be used for rabbitMQ
     */
    public Connection getConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(System.getenv("RABBITMQ"));
        factory.setVirtualHost(System.getenv("RABBITMQ_VIRTUAL"));
        factory.setUsername(System.getenv("RABBITMQ_USER"));
        factory.setPassword(System.getenv("RABBITMQ_PASS"));
        factory.setPort(Integer.parseInt(System.getenv("RABBITMQ_PORT")));

        return factory.newConnection();
    }

    /**
     * @return Returns a channel that can be used for rabbitMQ
     */
    public Channel getChannel(Connection connection) throws Exception {
        return connection.createChannel();
    }

    /**
     * Close the channel and/or connection for rabbitMQ if they have been initialized
     */
    public void closeConnection(Connection connection, Channel channel) throws Exception {
        if (channel != null) {
            channel.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}

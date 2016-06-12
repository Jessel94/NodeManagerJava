package hro.ictlab.nodemanager.connectors;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * This class is used to setup and close all connections for the RabbitApi.
 */
public class RabbitApiConnector {

    /**
     * @return Returns a HttpClient which can be used to connect to the rabbitMQ Api
     */
    public CloseableHttpClient getConnection() {
        return HttpClientBuilder.create().build();
    }

    /**
     * Close the HttpClient if it has been initialized
     */
    public void closeConnection(CloseableHttpClient client) throws Exception {
        if (client != null) {
            client.close();
        }
    }
}

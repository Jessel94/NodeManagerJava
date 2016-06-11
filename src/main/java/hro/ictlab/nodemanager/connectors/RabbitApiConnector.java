package hro.ictlab.nodemanager.connectors;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class RabbitApiConnector {

    public CloseableHttpClient getConnection() {
        return HttpClientBuilder.create().build();
    }

    public void closeConnection(CloseableHttpClient client) throws Exception {
        if (client != null) {
            client.close();
        }
    }
}

package hro.ictlab.nodemanager.connectors;

import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Test;

public class RabbitApiConnectorTest {

    private final RabbitApiConnector rabbitApiConnector = new RabbitApiConnector();

    @Test
    public void getConnection() throws Exception {
        CloseableHttpClient closeableHttpClient = rabbitApiConnector.getConnection();
        Assert.assertNotNull(closeableHttpClient);
    }

    @Test
    public void closeConnection() throws Exception {
        rabbitApiConnector.closeConnection(null);
    }

}
package hro.ictlab.nodemanager.connectors;

import org.junit.Test;

public class RabbitConnectorTest {

    private final RabbitConnector rabbitConnector = new RabbitConnector();

    @Test(expected = NumberFormatException.class)
    public void getConnection() throws Exception {
        rabbitConnector.getConnection();
    }

    @Test(expected = NullPointerException.class)
    public void getChannel() throws Exception {
        rabbitConnector.getChannel(null);
    }

    @Test
    public void closeConnection() throws Exception {
        rabbitConnector.closeConnection(null, null);
    }

}
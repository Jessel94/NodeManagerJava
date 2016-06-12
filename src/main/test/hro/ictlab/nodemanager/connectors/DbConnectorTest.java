package hro.ictlab.nodemanager.connectors;

import org.junit.Test;

public class DbConnectorTest {

    private final DbConnector dbConnector = new DbConnector();

    @Test(expected = com.mysql.jdbc.exceptions.jdbc4.CommunicationsException.class)
    public void getConnection() throws Exception {
        dbConnector.getConnection();
    }

    @Test
    public void closeConnection() throws Exception {
        dbConnector.closeConnection(null);
    }

}
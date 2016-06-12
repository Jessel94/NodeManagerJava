package hro.ictlab.nodemanager.database;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class is used to test the DbHandler Class
 */
public class DbHandlerTest {

    private final DbHandler dbHandler = new DbHandler();

    /**
     * This test checks if the response is false if you input a invalid command
     */
    @Test
    public void isCommandValid() throws Exception {
        boolean result = dbHandler.isCommandValid("invalid command");
        Assert.assertEquals(false, result);

    }

}
package hro.ictlab.nodemanager.database;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class is used to test the MessageBuilder Class
 */
public class MessageBuilderTest {

    /**
     * This test checks if the response is NULL if you input a invalid command
     */
    @Test
    public void generateMessage() throws Exception {
        String result = MessageBuilder.generateMessage("invalid command");
        Assert.assertNull(result);
    }

    /**
     * This test checks if the response is correct if you input a valid command
     */
    @Test
    public void generateMessageStart() throws Exception {
        String result = MessageBuilder.generateMessage("start");
        Assert.assertEquals("Starting", result);
    }

    /**
     * This test checks if the response is correct if you input a valid command
     */
    @Test
    public void generateMessageStop() throws Exception {
        String result = MessageBuilder.generateMessage("stop");
        Assert.assertEquals("Stopping", result);
    }

    /**
     * This test checks if the response is correct if you input a valid command
     */
    @Test
    public void generateMessageRestart() throws Exception {
        String result = MessageBuilder.generateMessage("restart");
        Assert.assertEquals("Restarting", result);
    }

    /**
     * This test checks if the response is correct if you input a valid command
     */
    @Test
    public void generateMessageDelete() throws Exception {
        String result = MessageBuilder.generateMessage("delete");
        Assert.assertEquals("Deleting", result);
    }

}
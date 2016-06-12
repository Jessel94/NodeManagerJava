package hro.ictlab.nodemanager.database;

import org.junit.Assert;
import org.junit.Test;

public class MessageBuilderTest {

    @Test
    public void generateMessage() throws Exception {
        String result = MessageBuilder.generateMessage("invalid command");
        Assert.assertNull(result);
    }

}
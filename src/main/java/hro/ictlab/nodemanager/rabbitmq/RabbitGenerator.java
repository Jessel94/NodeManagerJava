package hro.ictlab.nodemanager.rabbitmq;

import java.math.BigInteger;
import java.security.SecureRandom;

class RabbitGenerator {

    private final SecureRandom random = new SecureRandom();

    private String nextSessionId() {
        return new BigInteger(130, random).toString(28);
    }

    String generateUser() {
        return nextSessionId();
    }

    String generatePass() {
        return nextSessionId();
    }
}

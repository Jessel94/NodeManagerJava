package hro.ictlab.nodemanager.rabbitmq;

import java.math.BigInteger;
import java.security.SecureRandom;

class Generator {

    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(130, random).toString(28);
    }

    String generateUser() {
        return nextSessionId();
    }

    String generatePass() {
        return nextSessionId();
    }
}

package hro.ictlab.nodemanager.services;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Generator {

    private final SecureRandom random = new SecureRandom();

    private String nextSessionId() {
        return new BigInteger(130, random).toString(28);
    }

    public String generateUser() {
        return nextSessionId();
    }

    public String generatePass() {
        return nextSessionId();
    }
}

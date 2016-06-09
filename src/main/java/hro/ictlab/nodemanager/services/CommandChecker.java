package hro.ictlab.nodemanager.services;

public class CommandChecker {

    public boolean isValidCommand(String command) {
        if (command.equals("start")) {
            return true;
        }
        if (command.equals("stop")) {
            return true;
        }
        if (command.equals("restart")) {
            return true;
        }
        return false;
    }
}

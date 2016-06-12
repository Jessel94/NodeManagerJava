package hro.ictlab.nodemanager.database;

class MessageBuilder {


    static String generateMessage(String command) {
        if (command.equals("start")) {
            return "Starting";
        }
        if (command.equals("stop")) {
            return "Stopping";
        }
        if (command.equals("restart")) {
            return "Restarting";
        }
        if (command.equals("delete")) {
            return "Deleting";
        }
        return null;
    }
}

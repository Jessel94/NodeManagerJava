package hro.ictlab.nodemanager.database;

class MessageBuilder {

    String generateMessage(String command) {
        if (command.equals("start")) {
            return generateStart();
        }
        if (command.equals("stop")) {
            return generateStop();
        }
        if (command.equals("restart")) {
            return generateRestart();
        }
        return null;
    }

    private String generateStart() {
        return "Starting";
    }

    private String generateStop() {
        return "Stopping";
    }

    private String generateRestart() {
        return "Restarting";
    }
}

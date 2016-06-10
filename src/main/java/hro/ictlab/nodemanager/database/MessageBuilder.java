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
        if (command.equals("delete")) {
            return generateDelete();
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

    private String generateDelete() {
        return "Deleting";
    }
}

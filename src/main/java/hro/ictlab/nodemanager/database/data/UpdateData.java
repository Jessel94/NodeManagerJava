package hro.ictlab.nodemanager.database.data;

class UpdateData {

    String containerStatus(String containerId, String newStatus) {
        return ("UPDATE Containers SET state='" + newStatus + "' WHERE id = " + containerId);
    }

    String nodeStatus(String queueId) {
        return ("UPDATE Nodes SET lastchecked = NULL WHERE queueid = " + queueId);
    }
}


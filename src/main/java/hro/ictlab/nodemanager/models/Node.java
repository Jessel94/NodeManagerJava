package hro.ictlab.nodemanager.models;

import java.sql.Timestamp;

/**
 * Model that is used for Node data
 */
public class Node {
    private int id;
    private String name;
    private Timestamp lastChecked;
    private int queueId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastChecked(Timestamp lastChecked) {
        this.lastChecked = lastChecked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }
}

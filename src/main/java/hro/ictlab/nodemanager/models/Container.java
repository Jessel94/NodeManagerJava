package hro.ictlab.nodemanager.models;

import java.sql.Timestamp;

/**
 * Model that is used for container data
 */
public class Container {
    private int id;
    private int queueId;
    private String name;
    private Timestamp creationDate;
    private String state;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public void setState(String state) {
        this.state = state;
    }
}

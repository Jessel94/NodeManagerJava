package hro.ictlab.nodemanager.models;

import java.sql.Timestamp;

public class Container {
    private int id;
    private int queueid;
    private String name;
    private Timestamp creationdate;
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQueueid() {
        return queueid;
    }

    public void setQueueid(int queueid) {
        this.queueid = queueid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Timestamp creationdate) {
        this.creationdate = creationdate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

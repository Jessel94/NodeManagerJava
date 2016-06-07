package hro.ictlab.nodemanager.models;

import java.sql.Timestamp;

public class Node {
    private int id;
    private String name;
    private Timestamp lastchecked;
    private int queueid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLastchecked() {
        return lastchecked;
    }

    public void setLastchecked(Timestamp lastchecked) {
        this.lastchecked = lastchecked;
    }

    public int getQueueid() {
        return queueid;
    }

    public void setQueueid(int queueid) {
        this.queueid = queueid;
    }
}

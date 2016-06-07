package hro.ictlab.nodemanager.models;

public class Container {
    private int id;
    private int queueid;
    private String name;
    private String creationdate;
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

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

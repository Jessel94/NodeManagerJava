package hro.ictlab.nodemanager.models;

public class Queue {
    private int id;
    private String hostname;
    private String queuename;
    private String queuepass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setQueuename(String queuename) {
        this.queuename = queuename;
    }

    public void setQueuepass(String queuepass) {
        this.queuepass = queuepass;
    }
}

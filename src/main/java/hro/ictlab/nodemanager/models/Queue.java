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

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getQueuename() {
        return queuename;
    }

    public void setQueuename(String queuename) {
        this.queuename = queuename;
    }

    public String getQueuepass() {
        return queuepass;
    }

    public void setQueuepass(String queuepass) {
        this.queuepass = queuepass;
    }
}

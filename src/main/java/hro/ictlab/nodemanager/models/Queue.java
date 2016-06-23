package hro.ictlab.nodemanager.models;

/**
 * Model that is used for Queue data
 */
public class Queue {
    private int id;
    private String hostName;
    private String queueName;
    private String queuePass;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueuePass() {
        return queuePass;
    }

    public void setQueuePass(String queuePass) {
        this.queuePass = queuePass;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

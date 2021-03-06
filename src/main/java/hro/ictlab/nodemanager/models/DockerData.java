package hro.ictlab.nodemanager.models;

/**
 * Model that is used for DockerData
 */
public class DockerData {
    private String containerID;
    private String status;

    public String getContainerID() {
        return containerID;
    }

    public void setContainerID(String containerID) {
        this.containerID = containerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

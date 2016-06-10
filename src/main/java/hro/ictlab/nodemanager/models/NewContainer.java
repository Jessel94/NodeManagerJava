package hro.ictlab.nodemanager.models;

import com.google.gson.annotations.SerializedName;

public class NewContainer {

    @SerializedName("containerName")
    private String containerName;

    @SerializedName("node")
    private String node;

    @SerializedName("baseImage")
    private String baseImage;

    @SerializedName("hostPort")
    private String hostPort;

    @SerializedName("containerPort")
    private String containerPort;

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getBaseImage() {
        return baseImage;
    }

    public void setBaseImage(String baseImage) {
        this.baseImage = baseImage;
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }

    public String getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(String containerPort) {
        this.containerPort = containerPort;
    }
}

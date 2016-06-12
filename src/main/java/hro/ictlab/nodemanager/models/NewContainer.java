package hro.ictlab.nodemanager.models;

import com.google.gson.annotations.SerializedName;

/**
 * Model that is used for the NewContainer POST request
 */
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

    public String getNode() {
        return node;
    }

    public String getBaseImage() {
        return baseImage;
    }

    public String getHostPort() {
        return hostPort;
    }

    public String getContainerPort() {
        return containerPort;
    }

}

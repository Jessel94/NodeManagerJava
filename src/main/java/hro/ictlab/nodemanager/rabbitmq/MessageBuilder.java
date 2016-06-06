package hro.ictlab.nodemanager.rabbitmq;

import org.json.JSONArray;
import org.json.JSONObject;

class MessageBuilder {

    String main(String ContainerID, String message) {
        if (message.equals("stop")) {
            return Stop(ContainerID);
        }
        if (message.equals("start")) {
            return Start(ContainerID);
        } else {
            return "no valid input";
        }
    }

    private String Start(String containerID) {
        return "not yet done";
    }

    private String Stop(String containerID) {
        JSONObject jo1 = new JSONObject();
        jo1.put("container", containerID);
        jo1.put("time", "60");

        JSONObject jo2 = new JSONObject();
        jo2.put("stop", jo1);

        JSONObject mainObj = new JSONObject();
        mainObj.put("action", "container");
        mainObj.put("docker", jo2);

        JSONArray mainObjArray = new JSONArray();
        mainObjArray.put(mainObj);

        return mainObjArray.toString();
    }
}

package hro.ictlab.nodemanager.rabbitmq;

import org.json.JSONArray;
import org.json.JSONObject;

class MessageBuilder {

    String main(String ContainerID, String message) {
        JSONObject jo = new JSONObject();
        if (message.equals("start")) {
            jo = Start(ContainerID);
        }
        if (message.equals("stop")) {
            jo = Stop(ContainerID);
        }
        if (message.equals("restart")) {
            jo = Restart(ContainerID);
        }

        JSONObject mainObj = new JSONObject();
        mainObj.put("action", "container");
        mainObj.put("docker", jo);

        JSONArray mainObjArray = new JSONArray();
        mainObjArray.put(mainObj);

        return mainObjArray.toString();
    }

    private JSONObject Start(String containerID) {
        JSONObject jo1 = new JSONObject();
        jo1.put("container", containerID);

        JSONObject jo2 = new JSONObject();
        jo2.put("start", jo1);

        return jo2;
    }

    private JSONObject Stop(String containerID) {
        JSONObject jo1 = new JSONObject();
        jo1.put("container", containerID);
        jo1.put("time", "60");

        JSONObject jo2 = new JSONObject();
        jo2.put("stop", jo1);

        return jo2;
    }

    private JSONObject Restart(String containerID) {
        JSONObject jo1 = new JSONObject();
        jo1.put("container", containerID);
        jo1.put("time", "60");

        JSONObject jo2 = new JSONObject();
        jo2.put("restart", jo1);

        return jo2;
    }
}

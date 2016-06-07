package hro.ictlab.nodemanager.rabbitmq;

import org.json.JSONArray;
import org.json.JSONObject;

class MessageBuilder {

    String main(String iD, String message, String userName, String passWord) {
        JSONArray mainObjArray = new JSONArray();
        if (message.equals("start")) {
            mainObjArray = actionContainer(Start(iD));
        }
        if (message.equals("stop")) {
            mainObjArray = actionContainer(Stop(iD));
        }
        if (message.equals("restart")) {
            mainObjArray = actionContainer(Restart(iD));
        }
        if (message.equals("setid")) {
            mainObjArray.put(actionId(iD));
            mainObjArray.put(actionConnect(rabbitMQ(iD, userName, passWord)));
        }

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

    private JSONArray actionContainer(JSONObject jo) {
        JSONObject mainObj = new JSONObject();
        mainObj.put("action", "container");
        mainObj.put("docker", jo);

        JSONArray mainObjArray = new JSONArray();
        mainObjArray.put(mainObj);

        return mainObjArray;
    }

    private JSONObject actionId(String queueId) {
        JSONObject mainObj = new JSONObject();
        mainObj.put("action", "set-id");
        mainObj.put("id", queueId);

        return mainObj;
    }

    private JSONObject actionConnect(JSONObject jo) {
        JSONObject mainObj = new JSONObject();
        mainObj.put("amqp", jo);
        mainObj.put("action", "connect");

        return mainObj;
    }

    private JSONObject rabbitMQ(String queueId, String userName, String passWord) {
        JSONObject jo = new JSONObject();
        jo.put("host", System.getenv("RABBITMQ"));
        jo.put("virtualhost", System.getenv("RABBITMQ_VIRTUAL"));
        jo.put("port", System.getenv("RABBITMQ_PORT"));
        jo.put("username", userName);
        jo.put("password", passWord);
        jo.put("queue", queueId);

        return jo;
    }

}

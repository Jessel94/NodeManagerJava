package hro.ictlab.nodemanager.rabbitmq;

import org.json.JSONArray;
import org.json.JSONObject;

class MessageBuilder {

    String buildMessage(String id, String message, String userName, String passWord, String nodePort, String containerPort, String image) {
        JSONArray mainObjArray = new JSONArray();
        if (message.equals("start")) {
            mainObjArray = actionContainer(start(id));
        }
        if (message.equals("stop")) {
            mainObjArray = actionContainer(stop(id));
        }
        if (message.equals("restart")) {
            mainObjArray = actionContainer(restart(id));
        }
        if (message.equals("delete")) {
            mainObjArray = actionContainer(delete(id));
        }
        if (message.equals("create")) {
            JSONObject temp = create(id, nodePort, containerPort, image);
            mainObjArray = actionContainer(temp);
        }
        if (message.equals("setid")) {
            mainObjArray.put(actionId(id));
            mainObjArray.put(actionConnect(rabbitMQ(id, userName, passWord)));
        }

        return mainObjArray.toString();
    }

    private JSONObject start(String containerId) {
        JSONObject jo2 = new JSONObject();
        jo2.put("start", containerId);

        return jo2;
    }

    private JSONObject stop(String containerId) {
        JSONObject jo1 = new JSONObject();
        jo1.put("container", containerId);
        jo1.put("time", "60");

        JSONObject jo2 = new JSONObject();
        jo2.put("stop", jo1);

        return jo2;
    }

    private JSONObject restart(String containerId) {
        JSONObject jo1 = new JSONObject();
        jo1.put("container", containerId);
        jo1.put("time", "60");

        JSONObject jo2 = new JSONObject();
        jo2.put("restart", jo1);

        return jo2;
    }

    private JSONObject delete(String containerId) {
        JSONObject jo1 = new JSONObject();
        jo1.put("container", containerId);
        jo1.put("force", true);

        JSONObject jo2 = new JSONObject();
        jo2.put("remove", jo1);

        return jo2;
    }

    private JSONObject create(String containerId, String nodePort, String containerPort, String image) {
        JSONObject jo1 = new JSONObject();

        JSONObject jo2 = new JSONObject();
        jo2.put(nodePort, containerPort);

        JSONObject jo3 = new JSONObject();
        jo3.put("name", containerId);
        jo3.put("environment", jo1);
        jo3.put("ports", jo2);
        jo3.put("image", image);
        jo3.put("detached", true);

        JSONObject jo4 = new JSONObject();
        jo4.put("run", jo3);

        return jo4;
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

package main.java.hro.ictlab.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class QueueRepository {

    public JSONArray getAllQueues() throws IOException {
        JSONArray jsonArray = getData();
        return jsonArray;
    }

    private JSONArray getData() throws  IOException {
        JSONObject jo = new JSONObject();
        jo.put("hostname", "http://145.24.222.140/");
        jo.put("queueid", "1");
        jo.put("queuename", "dockname");
        jo.put("queeupass", "dockpass");

        JSONObject jo2 = new JSONObject();
        jo2.put("hostname", "http://145.24.222.140/");
        jo2.put("queueid", "45");
        jo2.put("queuename", "swarmname");
        jo2.put("queeupass", "sawrmpass");

        JSONArray ja = new JSONArray();
        ja.put(jo);
        ja.put(jo2);

        return  ja;
    }
}

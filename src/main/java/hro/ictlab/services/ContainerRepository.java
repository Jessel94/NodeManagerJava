package main.java.hro.ictlab.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class ContainerRepository {

    public JSONArray getAllContainers() throws IOException {
        JSONArray jsonArray = getData();
        return jsonArray;
    }

    private JSONArray getData() throws  IOException {
        JSONObject jo = new JSONObject();
        jo.put("id", "1");
        jo.put("name", "containerOne");
        jo.put("creationDate", "2016-03-21T08:37:00.000Z");
        jo.put("state", "stopped");
        jo.put("queueid", "1");

        JSONObject jo2 = new JSONObject();
        jo2.put("id", "2");
        jo2.put("name", "containerTwo");
        jo2.put("creationDate", "2016-03-20T16:37:36.000Z");
        jo2.put("state", "started");
        jo2.put("queueid", "2");

        JSONArray ja = new JSONArray();
        ja.put(jo);
        ja.put(jo2);

        return  ja;
    }
}

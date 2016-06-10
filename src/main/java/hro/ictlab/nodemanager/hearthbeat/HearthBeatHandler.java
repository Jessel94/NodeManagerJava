package hro.ictlab.nodemanager.hearthbeat;

import hro.ictlab.nodemanager.models.DockerData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HearthBeatHandler {

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public String readHeartBeat(String ip) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL("http://" + ip + ":53452").openConnection();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(InputStream in = conn.getInputStream()) {
            byte[] buffer = new byte[2048];
            int read;
            while((read = in.read(buffer)) > 0) {
                out.write(buffer, 0, read);
            }
        }
        return out.toString();
    }

    public ArrayList<DockerData> fillDockerData(JSONArray jsonArray) throws Exception {
        JSONObject dockerJson;
        ArrayList<DockerData> dockerDatas = new ArrayList<>(jsonArray.length());
        for (int i=0; i < jsonArray.length(); i++) {
            try {
                dockerJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            DockerData dockerData = fillDockerData(dockerJson);
            if (dockerData != null) {
                dockerDatas.add(dockerData);
            }
        }
        return dockerDatas;
    }

    private DockerData fillDockerData(JSONObject jsonObject) {
        DockerData dockerData = new DockerData();
        //temp bugfix until node works
        if (jsonObject.has("NAMES") | jsonObject.has("PORTS")) {
            String containerID = null;
            if(jsonObject.has("PORTS")){
                containerID = jsonObject.getString("PORTS");
            }
            if(jsonObject.has("NAMES")){
                containerID = jsonObject.getString("NAMES");
            }
            String containerCheck = containerID.replaceAll("container", "");
            if (isInteger(containerCheck)) {
                dockerData.setStatus(jsonObject.getString("STATUS"));
                dockerData.setContainerID(containerCheck);
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
        // Return new object
        return dockerData;
    }
}

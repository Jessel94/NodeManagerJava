package hro.ictlab.nodemanager.heartbeat;

import hro.ictlab.nodemanager.models.DockerData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

class DataFormatter {

    private static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    private static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

    ArrayList<DockerData> fillDockerData(JSONArray jsonArray) {
        JSONObject dockerJson;
        ArrayList<DockerData> dockerDatas = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
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
            if (jsonObject.has("PORTS")) {
                containerID = jsonObject.getString("PORTS");
            }
            if (jsonObject.has("NAMES")) {
                containerID = jsonObject.getString("NAMES");
            }
            String containerCheck = containerID.replaceAll("container", "");
            if (isInteger(containerCheck)) {
                dockerData.setStatus(jsonObject.getString("STATUS"));
                dockerData.setContainerID(containerCheck);
            } else {
                return null;
            }
        } else {
            return null;
        }
        // Return new object
        return dockerData;
    }
}

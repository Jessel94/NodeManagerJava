package hro.ictlab.nodemanager.hearthbeat;

import hro.ictlab.nodemanager.models.DockerData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HearthBeatHandler {

    private final DataFormatter dataFormatter = new DataFormatter();
    private final HearthBeatReader hearthBeatReader = new HearthBeatReader();

    public ArrayList<DockerData> handleHearthBeat(String ip) throws Exception {
        String hearthBeatOutput = hearthBeatReader.readHeartBeat(ip);
        JSONObject outputJsonObject = new JSONObject(hearthBeatOutput);
        JSONArray outputJsonArray = outputJsonObject.getJSONArray("containers");
        return dataFormatter.fillDockerData(outputJsonArray);
    }
}

package hro.ictlab.nodemanager.heartbeat;

import hro.ictlab.nodemanager.models.DockerData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class that is used to process the hearthbeat request
 */
public class HeartBeatHandler {

    private final DataFormatter dataFormatter = new DataFormatter();
    private final HeartBeatReader hearthBeatReader = new HeartBeatReader();

    /**
     * Method that is used to handle the GET requests for a list of containers
     *
     * @param ip The ip adres of the node you want data from
     * @return returns an arrayList of all data from the specific node
     */
    public ArrayList<DockerData> handleHearthBeat(String ip) throws Exception {
        String hearthBeatOutput = hearthBeatReader.readHeartBeat(ip);
        JSONObject outputJsonObject = new JSONObject(hearthBeatOutput);
        JSONArray outputJsonArray = outputJsonObject.getJSONArray("containers");
        return dataFormatter.fillDockerData(outputJsonArray);
    }
}

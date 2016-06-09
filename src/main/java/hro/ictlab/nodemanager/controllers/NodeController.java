package hro.ictlab.nodemanager.controllers;

import hro.ictlab.nodemanager.database.DatabaseHandler;
import hro.ictlab.nodemanager.hearthbeat.HearthBeatHandler;
import hro.ictlab.nodemanager.models.DockerData;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/nodes/")
public class NodeController {

    private final DatabaseHandler databaseHandler = new DatabaseHandler();
    private final HearthBeatHandler hearthBeatHandler = new HearthBeatHandler();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueues() throws Exception {
        return Response.ok().entity(databaseHandler.nodeRequest(null)).build();
    }

    @GET
    @Path("{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueueById(@PathParam("id") String id) throws Exception {
        return Response.ok().entity(databaseHandler.nodeRequest(id)).build();
    }

    @GET
    @Path("/hb/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNodeHb() throws Exception {
        String output = hearthBeatHandler.readHeartBeat("145.24.222.140");
        return Response.ok().entity(output).build();
    }

    @GET
    @Path("/hb2/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNodeHb2() throws Exception {
        String output = hearthBeatHandler.readHeartBeat("145.24.222.140");
        JSONObject jsnobject = new JSONObject(output);
        JSONArray jsonArray = jsnobject.getJSONArray("containers");
        ArrayList<DockerData> outputData = hearthBeatHandler.fillDockerData(jsonArray);
        JSONArray jsArray = new JSONArray(outputData);
        return Response.ok().entity(jsArray.toString()).build();
    }
}

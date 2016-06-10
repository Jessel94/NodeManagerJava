package hro.ictlab.nodemanager.controllers;

import hro.ictlab.nodemanager.database.DbConnector;
import hro.ictlab.nodemanager.database.DbHandler;
import hro.ictlab.nodemanager.hearthbeat.HearthBeatHandler;
import hro.ictlab.nodemanager.models.DockerData;
import hro.ictlab.nodemanager.models.Node;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;

@Path("/nodes/")
public class NodeController {

    private final DbHandler dbHandler = new DbHandler();
    private final HearthBeatHandler hearthBeatHandler = new HearthBeatHandler();
    private final DbConnector dbConnector = new DbConnector();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueues() throws Exception {
        Connection conn = null;
        try {
            conn = dbConnector.getConnection();
            return Response.ok().entity(dbHandler.nodeRequest(null, conn)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            try {
                dbConnector.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GET
    @Path("{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueueById(@PathParam("id") String nodeId) throws Exception {
        Connection conn = null;
        try {
            conn = dbConnector.getConnection();
            return Response.ok().entity(dbHandler.nodeRequest(nodeId, conn)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            try {
                dbConnector.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GET
    @Path("/hb/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNodeHb() throws Exception {
        Connection conn = null;
        try {
            conn = dbConnector.getConnection();
            JSONArray jsArray = new JSONArray();
            ArrayList<Node> nodes = dbHandler.nodeList(conn);
            for(Node node : nodes){
                String ip = node.getName();
                String queueId= String.valueOf(node.getQueueid());

                String output = hearthBeatHandler.readHeartBeat(ip);
                JSONObject outputJsonObject = new JSONObject(output);
                JSONArray outputJsonArray = outputJsonObject.getJSONArray("containers");
                ArrayList<DockerData> outputData = hearthBeatHandler.fillDockerData(outputJsonArray);

                dbHandler.updateContainerList(outputData, conn);
                dbHandler.updateNode(queueId, conn);

                jsArray.put(outputData);
            }
            return Response.ok().entity(jsArray.toString()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            try {
                dbConnector.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GET
    @Path("/hb2/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNodeHb2() throws Exception {
        String output = hearthBeatHandler.readHeartBeat("145.24.222.140");
        return Response.ok().entity(output).build();
    }
}

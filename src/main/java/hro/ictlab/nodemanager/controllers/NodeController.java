package hro.ictlab.nodemanager.controllers;

import hro.ictlab.nodemanager.connectors.DbConnector;
import hro.ictlab.nodemanager.database.DbHandler;
import hro.ictlab.nodemanager.hearthbeat.HearthBeatHandler;
import hro.ictlab.nodemanager.models.DockerData;
import hro.ictlab.nodemanager.models.Node;

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
            ArrayList<Node> nodes = dbHandler.nodeList(conn);
            for (Node node : nodes) {
                String ip = node.getName();
                String queueId = String.valueOf(node.getQueueId());

                ArrayList<DockerData> outputData = hearthBeatHandler.handleHearthBeat(ip);

                dbHandler.updateContainerList(outputData, conn);
                dbHandler.updateNode(queueId, conn);
            }
            return Response.ok().build();
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
}

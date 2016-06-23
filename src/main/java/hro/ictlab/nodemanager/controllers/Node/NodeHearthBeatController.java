package hro.ictlab.nodemanager.controllers.node;

import hro.ictlab.nodemanager.connectors.DbConnector;
import hro.ictlab.nodemanager.database.DbHandler;
import hro.ictlab.nodemanager.heartbeat.HeartBeatHandler;
import hro.ictlab.nodemanager.models.DockerData;
import hro.ictlab.nodemanager.models.Node;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Class that is used to handle the hearthbeat requests
 */
@Path("/nodes/")
public class NodeHearthBeatController {

    private final DbHandler dbHandler = new DbHandler();
    private final HeartBeatHandler hearthBeatHandler = new HeartBeatHandler();
    private final DbConnector dbConnector = new DbConnector();

    /**
     * Method used to update all containers by requesting the hearthbeat of all known nodes
     *
     * @return Returns a OK if the command is succesfully processed, otherwise returns a serverError.
     */
    @GET
    @Path("/hb/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNodeHb() throws Exception {
        Connection conn = null;
        try {
            //Starting up all the required connections
            conn = dbConnector.getConnection();

            //For each nodes in the list of nodes it handles the hearthBeat
            ArrayList<Node> nodes = dbHandler.nodeList(conn);
            for (Node node : nodes) {
                String ip = node.getName();
                String queueId = String.valueOf(node.getQueueId());
                try {
                    ArrayList<DockerData> outputData = hearthBeatHandler.handleHearthBeat(ip);

                    dbHandler.updateContainerList(outputData, conn);
                    dbHandler.updateNode(queueId, conn);
                } catch (Exception e) {
                    dbHandler.deleteNode(ip, conn);
                    dbHandler.deleteQueue(queueId, conn);
                    dbHandler.updateContainerCrash(queueId, conn);
                }
            }
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            try {
                //Close all connections if open
                dbConnector.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

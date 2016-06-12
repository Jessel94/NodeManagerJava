package hro.ictlab.nodemanager.controllers.node;

import hro.ictlab.nodemanager.connectors.DbConnector;
import hro.ictlab.nodemanager.database.DbHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;

/**
 * Class that is used to handle the lists of all the nodes.
 */
@Path("/nodes/")
public class NodeListController {

    private final DbHandler dbHandler = new DbHandler();
    private final DbConnector dbConnector = new DbConnector();

    /**
     * Method used to get a list of all nodes
     *
     * @return Returns a list of all nodes in JSON format if succesfull, otherwise returns a serverError
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNodes() throws Exception {
        return getResponse(null);
    }

    /**
     * Method used to get the data from a specific node
     *
     * @param nodeId The id of the node you would like to get the info from.
     * @return Returns a node in JSON format if succesfull, otherwise returns a serverError
     */
    @GET
    @Path("{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNodeById(@PathParam("id") String nodeId) throws Exception {
        return getResponse(nodeId);
    }

    private Response getResponse(String nodeId) throws Exception {
        Connection conn = null;
        try {
            //Starting up all the required connections
            conn = dbConnector.getConnection();

            //Handling the command
            return Response.ok().entity(dbHandler.nodeRequest(nodeId, conn)).build();
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

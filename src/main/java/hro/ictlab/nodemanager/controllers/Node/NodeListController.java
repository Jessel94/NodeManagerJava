package hro.ictlab.nodemanager.controllers.Node;

import hro.ictlab.nodemanager.connectors.DbConnector;
import hro.ictlab.nodemanager.database.DbHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;

@Path("/nodes/")
public class NodeListController {

    private final DbHandler dbHandler = new DbHandler();
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
}

package hro.ictlab.nodemanager.controllers.container;

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
 * Class that is used to handle the GET requests for a list of containers
 */
@Path("/containers/")
public class ContainerListController {

    private final DbHandler dbHandler = new DbHandler();
    private final DbConnector dbConnector = new DbConnector();

    /**
     * Method used to get a list of all containers
     *
     * @return Returns a list of all containers in JSON format if successful, otherwise returns a serverError
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainers() throws Exception {
        return getResponse(null);
    }

    /**
     * Method used to get the data from a specific container
     *
     * @param containerId The id of the container you would like to get the info from.
     * @return Returns a container in JSON format if successful, otherwise returns a serverError
     */
    @GET
    @Path("{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainerById(@PathParam("id") String containerId) throws Exception {
        return getResponse(containerId);
    }

    private Response getResponse(String containerId) throws Exception {
        Connection conn = null;
        try {
            //Starting up all the required connections
            conn = dbConnector.getConnection();

            //Handling the command
            return Response.ok().entity(dbHandler.containerRequest(containerId, conn)).build();
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

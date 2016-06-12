package hro.ictlab.nodemanager.controllers.queue;

import com.rabbitmq.client.Channel;
import hro.ictlab.nodemanager.connectors.DbConnector;
import hro.ictlab.nodemanager.connectors.RabbitApiConnector;
import hro.ictlab.nodemanager.connectors.RabbitConnector;
import hro.ictlab.nodemanager.database.DbHandler;
import hro.ictlab.nodemanager.rabbitmq.RabbitHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.sql.Connection;

/**
 * Class that is used to handle the new nodes that request a queue
 */
@Path("/queues/")
public class QueueRequestController {

    private final RabbitHandler rabbitHandler = new RabbitHandler();
    private final DbHandler dbHandler = new DbHandler();
    private final DbConnector dbConnector = new DbConnector();
    private final RabbitConnector rabbitConnector = new RabbitConnector();
    private final RabbitApiConnector rabbitApiConnector = new RabbitApiConnector();

    /**
     * Method used to create a new queue and return all info about it.
     *
     * @param requestContext The requestContext holds the ip adres which is saved to the database to know what the adress of the node is on future requests
     * @return Returns a JSON formatted response holding the info for the new queue if the command is succesfully processed, otherwise returns a serverError.
     */
    @GET
    @Path("request/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestQueue(@Context HttpServletRequest requestContext, @Context SecurityContext context) throws Exception {
        Connection dbConn = null;
        com.rabbitmq.client.Connection rabbitConn = null;
        Channel rabbitChannel = null;
        CloseableHttpClient client = null;
        try {
            //Starting up all the required connections
            dbConn = dbConnector.getConnection();
            rabbitConn = rabbitConnector.getConnection();
            rabbitChannel = rabbitConnector.getChannel(rabbitConn);
            client = rabbitApiConnector.getConnection();

            //Handling the command
            String hostName = System.getenv("RABBITMQ");
            String userName = rabbitHandler.generateUserName();
            String passWord = rabbitHandler.generatePassWord();
            rabbitHandler.createUser(userName, passWord, client);
            int queueID = dbHandler.newQueue(hostName, userName, passWord, requestContext.getRemoteAddr(), dbConn);
            String output = rabbitHandler.requestQueue(queueID, userName, passWord, rabbitChannel);

            return Response.ok().entity(output).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            try {
                //Close all connections if open
                dbConnector.closeConnection(dbConn);
                rabbitConnector.closeConnection(rabbitConn, rabbitChannel);
                rabbitApiConnector.closeConnection(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

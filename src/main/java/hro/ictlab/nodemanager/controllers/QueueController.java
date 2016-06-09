package hro.ictlab.nodemanager.controllers;

import com.rabbitmq.client.Channel;
import hro.ictlab.nodemanager.database.DatabaseHandler;
import hro.ictlab.nodemanager.database.DbConnector;
import hro.ictlab.nodemanager.rabbitmq.RabbitApiConnector;
import hro.ictlab.nodemanager.rabbitmq.RabbitConnector;
import hro.ictlab.nodemanager.rabbitmq.RabbitmqHandler;
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

@Path("/queues/")
public class QueueController {

    private final RabbitmqHandler rabbitmqHandler = new RabbitmqHandler();
    private final DatabaseHandler databaseHandler = new DatabaseHandler();
    private final DbConnector dbConnector = new DbConnector();
    private final RabbitConnector rabbitConnector = new RabbitConnector();
    private final RabbitApiConnector rabbitApiConnector = new RabbitApiConnector();

    @GET
    @Path("request/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestQueue(@Context HttpServletRequest requestContext, @Context SecurityContext context) throws Exception {
        Connection dbConn = null;
        com.rabbitmq.client.Connection rabbitConn = null;
        Channel rabbitChannel = null;
        CloseableHttpClient client = null;
        try {
            dbConn = dbConnector.getConnection();
            rabbitConn = rabbitConnector.getConnection();
            rabbitChannel = rabbitConnector.getChannel(rabbitConn);
            client = rabbitApiConnector.getConnection();

            String hostName = System.getenv("RABBITMQ");
            String userName = rabbitmqHandler.generateUserName();
            String passWord = rabbitmqHandler.generatePassWord();
            rabbitmqHandler.createUser(userName, passWord, client);
            int queueID = databaseHandler.newQueue(hostName, userName, passWord, requestContext.getRemoteAddr(), dbConn);
            String output = rabbitmqHandler.requestQueue(queueID, userName, passWord, rabbitChannel);
            return Response.ok().entity(output).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            try {
                dbConnector.closeConnection(dbConn);
                rabbitConnector.closeConnection(rabbitConn, rabbitChannel);
                rabbitApiConnector.closeConnection(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

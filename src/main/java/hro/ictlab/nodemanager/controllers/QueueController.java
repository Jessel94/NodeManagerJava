package hro.ictlab.nodemanager.controllers;

import com.rabbitmq.client.Channel;
import hro.ictlab.nodemanager.database.DatabaseHandler;
import hro.ictlab.nodemanager.database.DbConnector;
import hro.ictlab.nodemanager.rabbitmq.RabbitConnector;
import hro.ictlab.nodemanager.rabbitmq.RabbitmqHandler;
import hro.ictlab.nodemanager.services.Generator;
import hro.ictlab.nodemanager.services.RabbitMQ;

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
    private final Generator generator = new Generator();
    private final RabbitMQ rabbitMQ = new RabbitMQ();

    @GET
    @Path("request/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestQueue(@Context HttpServletRequest requestContext, @Context SecurityContext context) throws Exception {
        Connection dbConn = null;
        com.rabbitmq.client.Connection rabbitConn = null;
        Channel rabbitChannel = null;
        try {
            dbConn = dbConnector.getConnection();
            rabbitConn = rabbitConnector.getConnection();
            rabbitChannel = rabbitConnector.getChannel(rabbitConn);

            String hostName = System.getenv("RABBITMQ");
            String userName = generator.generateUser();
            String passWord = generator.generatePass();
            rabbitMQ.createUser(userName, passWord);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

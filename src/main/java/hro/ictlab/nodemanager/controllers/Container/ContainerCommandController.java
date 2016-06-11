package hro.ictlab.nodemanager.controllers.Container;

import com.rabbitmq.client.Channel;
import hro.ictlab.nodemanager.connectors.DbConnector;
import hro.ictlab.nodemanager.connectors.RabbitConnector;
import hro.ictlab.nodemanager.database.DbHandler;
import hro.ictlab.nodemanager.rabbitmq.RabbitHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;

@Path("/containers/")
public class ContainerCommandController {

    private final DbHandler dbHandler = new DbHandler();
    private final RabbitHandler rabbitHandler = new RabbitHandler();
    private final DbConnector dbConnector = new DbConnector();
    private final RabbitConnector rabbitConnector = new RabbitConnector();


    @GET
    @Path("{id}/{command}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response issueContainerCommand(@PathParam("id") String containerId, @PathParam("command") String command) throws Exception {
        Connection dbConn = null;
        com.rabbitmq.client.Connection rabbitConn = null;
        Channel rabbitChannel = null;
        try {
            if (dbHandler.isCommandValid(command)) {
                dbConn = dbConnector.getConnection();
                rabbitConn = rabbitConnector.getConnection();
                rabbitChannel = rabbitConnector.getChannel(rabbitConn);

                String queueId = dbHandler.containerQueueID(containerId, dbConn);
                rabbitHandler.processCommand(containerId, queueId, command, rabbitChannel, null, null, null);
                dbHandler.updateContainer(containerId, command, dbConn, false);

                return Response.ok().build();
            }
            return Response.serverError().build();
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

    @GET
    @Path("{id}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response issueContainerCommand(@PathParam("id") String containerId) throws Exception {
        Connection dbConn = null;
        com.rabbitmq.client.Connection rabbitConn = null;
        Channel rabbitChannel = null;
        try {
            dbConn = dbConnector.getConnection();
            rabbitConn = rabbitConnector.getConnection();
            rabbitChannel = rabbitConnector.getChannel(rabbitConn);

            String queueId = dbHandler.containerQueueID(containerId, dbConn);
            rabbitHandler.processCommand(containerId, queueId, "delete", rabbitChannel, null, null, null);
            dbHandler.deleteContainer(containerId, dbConn);

            return Response.ok().build();
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

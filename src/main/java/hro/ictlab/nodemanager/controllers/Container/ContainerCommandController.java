package hro.ictlab.nodemanager.controllers.container;

import com.rabbitmq.client.Channel;
import hro.ictlab.nodemanager.connectors.DbConnector;
import hro.ictlab.nodemanager.connectors.RabbitConnector;
import hro.ictlab.nodemanager.database.DbHandler;
import hro.ictlab.nodemanager.models.Queue;
import hro.ictlab.nodemanager.rabbitmq.RabbitHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;

/**
 * Class that is used to handle the commands for the containers
 */
@Path("/containers/")
public class ContainerCommandController {

    private final DbHandler dbHandler = new DbHandler();
    private final RabbitHandler rabbitHandler = new RabbitHandler();
    private final DbConnector dbConnector = new DbConnector();
    private final RabbitConnector rabbitConnector = new RabbitConnector();


    /**
     * Method used to send a command to a specific container
     *
     * @param containerId The id of the container you would like to issue a command to.
     * @param command     The command you would like to send tot the container
     * @return Returns a OK if the command is succesfully processed, otherwise returns a serverError.
     */
    @GET
    @Path("{id}/{command}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response issueContainerCommand(@PathParam("id") String containerId, @PathParam("command") String command) throws Exception {
        Connection dbConn = null;
        com.rabbitmq.client.Connection rabbitConn = null;
        Channel rabbitChannel = null;
        try {
            if (dbHandler.isCommandValid(command)) {
                //Starting up all the required connections
                dbConn = dbConnector.getConnection();
                rabbitConn = rabbitConnector.getConnection();
                rabbitChannel = rabbitConnector.getChannel(rabbitConn);

                //Handling the command
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
                //Close all connections if open
                dbConnector.closeConnection(dbConn);
                rabbitConnector.closeConnection(rabbitConn, rabbitChannel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method used to send a delete command to a specific container
     *
     * @param containerId The id of the container you would like to issue a command to.
     * @return Returns a OK if the command is succesfully processed, otherwise returns a serverError.
     */
    @GET
    @Path("{id}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response issueDeleteContainer(@PathParam("id") String containerId) throws Exception {
        Connection dbConn = null;
        com.rabbitmq.client.Connection rabbitConn = null;
        Channel rabbitChannel = null;
        try {
            //Starting up all the required connections
            dbConn = dbConnector.getConnection();
            rabbitConn = rabbitConnector.getConnection();
            rabbitChannel = rabbitConnector.getChannel(rabbitConn);

            //Handling the command
            String queueId = dbHandler.containerQueueID(containerId, dbConn);
            rabbitHandler.processCommand(containerId, queueId, "delete", rabbitChannel, null, null, null);
            dbHandler.deleteContainer(containerId, dbConn);

            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            try {
                //Close all connections if open
                dbConnector.closeConnection(dbConn);
                rabbitConnector.closeConnection(rabbitConn, rabbitChannel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GET
    @Path("{id}/move/{nodeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response issueMoveContainer(@PathParam("id") String containerId, @PathParam("nodeId") String nodeId) throws Exception {
        Connection dbConn = null;
        com.rabbitmq.client.Connection rabbitConn = null;
        Channel rabbitChannel = null;
        try {
            //Starting up all the required connections
            dbConn = dbConnector.getConnection();
            rabbitConn = rabbitConnector.getConnection();
            rabbitChannel = rabbitConnector.getChannel(rabbitConn);

            //Handling the command
            String queueIdNew = dbHandler.nodeQueueID(nodeId, dbConn);
            Queue queueData = dbHandler.queueData(queueIdNew, dbConn);

            dbHandler.newContainer(dbHandler.containerName(containerId, dbConn), queueIdNew, dbConn);
            rabbitHandler.processExport(containerId, queueIdNew, "export", rabbitChannel, queueData.getQueueName(), queueData.getQueuePass());

            String queueIdOld = dbHandler.containerQueueID(containerId, dbConn);
            rabbitHandler.processCommand(containerId, queueIdOld, "delete", rabbitChannel, null, null, null);
            dbHandler.deleteContainer(containerId, dbConn);

            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            try {
                //Close all connections if open
                dbConnector.closeConnection(dbConn);
                rabbitConnector.closeConnection(rabbitConn, rabbitChannel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GET
    @Path("{id}/scale/{nodeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response issueScaleContainer(@PathParam("id") String containerId, @PathParam("nodeId") String nodeId) throws Exception {
        Connection dbConn = null;
        com.rabbitmq.client.Connection rabbitConn = null;
        Channel rabbitChannel = null;
        try {
            //Starting up all the required connections
            dbConn = dbConnector.getConnection();
            rabbitConn = rabbitConnector.getConnection();
            rabbitChannel = rabbitConnector.getChannel(rabbitConn);

            //Handling the command
            String queueIdNew = dbHandler.nodeQueueID(nodeId, dbConn);
            Queue queueData = dbHandler.queueData(queueIdNew, dbConn);

            dbHandler.newContainer(dbHandler.containerName(containerId, dbConn), queueIdNew, dbConn);
            rabbitHandler.processExport(containerId, queueIdNew, "export", rabbitChannel, queueData.getQueueName(), queueData.getQueuePass());

            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            try {
                //Close all connections if open
                dbConnector.closeConnection(dbConn);
                rabbitConnector.closeConnection(rabbitConn, rabbitChannel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

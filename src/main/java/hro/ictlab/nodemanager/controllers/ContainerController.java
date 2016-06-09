package hro.ictlab.nodemanager.controllers;

import com.rabbitmq.client.Channel;
import hro.ictlab.nodemanager.database.DatabaseHandler;
import hro.ictlab.nodemanager.database.DbConnector;
import hro.ictlab.nodemanager.rabbitmq.RabbitConnector;
import hro.ictlab.nodemanager.rabbitmq.RabbitmqHandler;
import hro.ictlab.nodemanager.services.CommandChecker;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;

@Path("/containers/")
public class ContainerController {

    private final DatabaseHandler databaseHandler = new DatabaseHandler();
    private final RabbitmqHandler rabbitmqHandler = new RabbitmqHandler();
    private final CommandChecker commandChecker = new CommandChecker();
    private final DbConnector dbConnector = new DbConnector();
    private final RabbitConnector rabbitConnector = new RabbitConnector();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainers() throws Exception {
        Connection conn = null;
        try {
            conn = dbConnector.getConnection();
            return Response.ok().entity(databaseHandler.containerRequest(null, conn)).build();
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
    public Response getContainerById(@PathParam("id") String containerId) throws Exception {
        Connection conn = null;
        try {
            conn = dbConnector.getConnection();
            return Response.ok().entity(databaseHandler.containerRequest(containerId, conn)).build();
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
    @Path("{id}/{command}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response issueContainerCommand(@PathParam("id") String containerId, @PathParam("command") String command) throws Exception {
        Connection dbConn = null;
        com.rabbitmq.client.Connection rabbitConn = null;
        Channel rabbitChannel = null;
        try {
            if (commandChecker.isValidCommand(command)) {
                dbConn = dbConnector.getConnection();
                rabbitConn = rabbitConnector.getConnection();
                rabbitChannel = rabbitConnector.getChannel(rabbitConn);
                String queueId = databaseHandler.containerQueueID(containerId, dbConn);
                String output = rabbitmqHandler.processCommand(containerId, queueId, command, rabbitChannel);
                databaseHandler.updateContainer(containerId, command, dbConn);
                return Response.ok().entity(output).build();
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newContainer(@Context HttpServletRequest request) throws Exception {
        return Response.ok("I Received Something").build();
    }
}

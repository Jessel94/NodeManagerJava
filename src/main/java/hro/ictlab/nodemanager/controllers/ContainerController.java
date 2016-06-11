package hro.ictlab.nodemanager.controllers;

import com.rabbitmq.client.Channel;
import hro.ictlab.nodemanager.PostDataFormatter;
import hro.ictlab.nodemanager.database.DbConnector;
import hro.ictlab.nodemanager.database.DbHandler;
import hro.ictlab.nodemanager.models.NewContainer;
import hro.ictlab.nodemanager.rabbitmq.RabbitConnector;
import hro.ictlab.nodemanager.rabbitmq.RabbitHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.sql.Connection;

@Path("/containers/")
public class ContainerController {

    private final DbHandler dbHandler = new DbHandler();
    private final RabbitHandler rabbitHandler = new RabbitHandler();
    private final DbConnector dbConnector = new DbConnector();
    private final RabbitConnector rabbitConnector = new RabbitConnector();
    private final PostDataFormatter postDataFormatter = new PostDataFormatter();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainers() throws Exception {
        Connection conn = null;
        try {
            conn = dbConnector.getConnection();
            return Response.ok().entity(dbHandler.containerRequest(null, conn)).build();
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
            return Response.ok().entity(dbHandler.containerRequest(containerId, conn)).build();
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


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newContainer(@Context HttpServletRequest request, @Context HttpServletResponse response) throws Exception {
        InputStream body = null;
        Connection dbConn = null;
        com.rabbitmq.client.Connection rabbitConn = null;
        Channel rabbitChannel = null;
        try {
            body = request.getInputStream();
            dbConn = dbConnector.getConnection();
            rabbitConn = rabbitConnector.getConnection();
            rabbitChannel = rabbitConnector.getChannel(rabbitConn);

            NewContainer requestModel = postDataFormatter.formatNewContainer(body);
            String queueId = dbHandler.nodeQueueID(requestModel.getNode(), dbConn);
            String containerId = dbHandler.newContainer(requestModel.getContainerName(), queueId, dbConn);
            rabbitHandler.processCommand(containerId, queueId, "create", rabbitChannel, requestModel.getHostPort(), requestModel.getContainerPort(), requestModel.getBaseImage());

            return Response.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            try {
                if (body != null) {
                    body.close();
                }
                dbConnector.closeConnection(dbConn);
                rabbitConnector.closeConnection(rabbitConn, rabbitChannel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

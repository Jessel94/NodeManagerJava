package hro.ictlab.nodemanager.controllers;

import hro.ictlab.nodemanager.database.DatabaseHandler;
import hro.ictlab.nodemanager.rabbitmq.RabbitmqHandler;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/containers/")
public class ContainerController {

    private final DatabaseHandler databaseHandler = new DatabaseHandler();
    private final RabbitmqHandler rabbitmqHandler = new RabbitmqHandler();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainers() throws Exception {
        return Response.ok().entity(databaseHandler.containerRequest(null)).build();
    }

    @GET
    @Path("{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainerById(@PathParam("id") String id) throws Exception {
        return Response.ok().entity(databaseHandler.containerRequest(id)).build();
    }

    @GET
    @Path("{id}/{command}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response issueContainerCommand(@PathParam("id") String id, @PathParam("command") String command) throws Exception {
        if (id != null & id != "null") {
            if (command != null & command != "null") {
                return Response.ok().entity(rabbitmqHandler.processCommand(id, command)).build();
            }
        }
        return Response.noContent().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newContainer(@Context HttpServletRequest request) throws Exception {
        return Response.ok("I Received Something").build();
    }
}

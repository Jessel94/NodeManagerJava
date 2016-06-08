package hro.ictlab.nodemanager.controllers;

import hro.ictlab.nodemanager.database.DatabaseHandler;
import hro.ictlab.nodemanager.rabbitmq.RabbitmqHandler;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/queues/")
public class QueueController {

    private final DatabaseHandler databaseHandler = new DatabaseHandler();
    private final RabbitmqHandler rabbitmqHandler = new RabbitmqHandler();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueues() throws Exception {
        return Response.ok().entity(databaseHandler.queueRequest(null)).build();
    }

    @GET
    @Path("{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueueById(@PathParam("id") String id) throws Exception {
        return Response.ok().entity(databaseHandler.queueRequest(id)).build();
    }

    @GET
    @Path("request/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestQueue(@Context HttpServletRequest requestContext, @Context SecurityContext context) throws Exception {
        return Response.ok().entity(rabbitmqHandler.requestQueue(requestContext.getRemoteAddr())).build();
    }
}

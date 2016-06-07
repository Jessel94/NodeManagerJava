package hro.ictlab.nodemanager.controllers;

import hro.ictlab.nodemanager.database.DatabaseHandler;
import hro.ictlab.nodemanager.rabbitmq.RabbitmqHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/queues/")
public class QueueController {

    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private RabbitmqHandler rabbitmqHandler = new RabbitmqHandler();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueues() throws Exception {
        String output = databaseHandler.queueRequest(null);
        return Response.ok().entity(output).build();
    }

    @GET
    @Path("{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueueById(@PathParam("id") String id) throws Exception {
        String output = databaseHandler.queueRequest(id);
        return Response.ok().entity(output).build();
    }

    @GET
    @Path("request/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestQueue() throws Exception {
        return Response.ok().entity(rabbitmqHandler.requestQueue()).build();
    }
}

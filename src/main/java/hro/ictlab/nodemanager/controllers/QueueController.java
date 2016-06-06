package hro.ictlab.nodemanager.controllers;

import hro.ictlab.nodemanager.database.ConnectionHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/queues")
public class QueueController {

    private ConnectionHandler connectionHandler = new ConnectionHandler();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueues() throws Exception {
        String output = connectionHandler.QueueRequest(null);
        return Response.ok().entity(output).build();
    }

    @GET
    @Path("/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueueById(@PathParam("id") String id) throws Exception {
        String output = connectionHandler.QueueRequest(id);
        return Response.ok().entity(output).build();
    }
}

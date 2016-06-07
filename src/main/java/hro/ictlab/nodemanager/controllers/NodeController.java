package hro.ictlab.nodemanager.controllers;

import hro.ictlab.nodemanager.database.DatabaseHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/nodes/")
public class NodeController {

    private final DatabaseHandler databaseHandler = new DatabaseHandler();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueues() throws Exception {
        String output = databaseHandler.nodeRequest(null);
        return Response.ok().entity(output).build();
    }

    @GET
    @Path("{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueueById(@PathParam("id") String id) throws Exception {
        String output = databaseHandler.nodeRequest(id);
        return Response.ok().entity(output).build();
    }
}

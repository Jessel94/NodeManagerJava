package hro.ictlab.nodemanager.controllers;

import hro.ictlab.nodemanager.services.QueueRepository;
import org.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/queues")
public class QueueController {

    private QueueRepository queueRepository = new QueueRepository();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueues() throws IOException {
        return Response.ok().entity(queueRepository.getAllQueues().toString()).build();
    }

    @GET
    @Path("/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueueById(@PathParam("id") String id) throws IOException {
        JSONArray jsonArray = queueRepository.getAllQueues();
        return Response.ok().entity(jsonArray.toString()).build();
    }
}

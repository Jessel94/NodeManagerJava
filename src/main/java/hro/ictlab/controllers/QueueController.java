package main.java.hro.ictlab.controllers;

import main.java.hro.ictlab.services.QueueRepository;
import org.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/docker")
public class QueueController {

    private QueueRepository queueRepository = new QueueRepository();

    @GET
    @Path("/queues/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueues() throws IOException {
        return Response.ok().entity(queueRepository.getAllQueues().toString()).build();
    }

    @GET
    @Path("/queues/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueueById(@PathParam("id") String id) throws IOException {
        JSONArray jsonArray = queueRepository.getAllQueues();
        return Response.ok().entity(jsonArray.toString()).build();
    }
}

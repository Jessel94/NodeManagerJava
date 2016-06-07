package hro.ictlab.nodemanager.controllers;


import hro.ictlab.nodemanager.rabbitmq.RabbitmqHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/request")
public class RequestController {

    private RabbitmqHandler rabbitmqHandler = new RabbitmqHandler();

    @GET
    @Path("/queue/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestQueue() throws Exception {
        return Response.ok().entity(rabbitmqHandler.RequestQueue()).build();
    }
}

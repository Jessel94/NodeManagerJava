package hro.ictlab.nodemanager.controllers;

import hro.ictlab.nodemanager.rabbitmq.RabbitmqHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/containers/")
public class CommandController {

    private RabbitmqHandler rabbitmqHandler = new RabbitmqHandler();

    @GET
    @Path("{id}/{command}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response issueCommand(@PathParam("id") String id, @PathParam("command") String command) throws Exception {
        if (id != null & id != "null") {
            if (command != null & command != "null") {
                return Response.ok().entity(rabbitmqHandler.processCommand(id, command)).build();
            }
        }
        return Response.noContent().build();
    }
}

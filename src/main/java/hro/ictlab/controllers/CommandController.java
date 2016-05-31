package main.java.hro.ictlab.controllers;

import main.java.hro.ictlab.rabbitmq.Send;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/containers")
public class CommandController {

    @GET
    @Path("/{id}/{command}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainerById(@PathParam("id") String id, @PathParam("command") String command) throws Exception {
        String output = Send.main(id, command);
        String message = null;

        if(output.equals("succes")) {
            message = new String("Message: " + command + ", has been added to the queue: " + id);
        }
        if(output.equals("failed")) {
            message = new String("Message: " + command + ", has been not been added to the queue: " + id);
        }

        return Response.ok().entity(message).build();
    }
}

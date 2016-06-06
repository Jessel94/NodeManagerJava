package hro.ictlab.nodemanager.controllers;


import hro.ictlab.nodemanager.rabbitmq.Send;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/containers")
public class CommandController {

    private Send send = new Send();

    @GET
    @Path("/{id}/{command}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainerById(@PathParam("id") String id, @PathParam("command") String command) throws Exception {
        return Response.ok().entity(send.main(id, command)).build();
    }
}

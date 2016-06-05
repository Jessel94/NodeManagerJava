package hro.ictlab.nodemanager.controllers;

import hro.ictlab.nodemanager.database.ConnectionHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/containers")
public class ContainerController {

    private ConnectionHandler connectionHandler = new ConnectionHandler();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainers() throws Exception {
        String output = connectionHandler.ContainerRequest(null);
        return Response.ok().entity(output).build();
    }

    @GET
    @Path("/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainerById(@PathParam("id") String id) throws Exception {
        String output = connectionHandler.ContainerRequest(id);
        return Response.ok().entity(output).build();
    }
}

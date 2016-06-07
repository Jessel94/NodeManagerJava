package hro.ictlab.nodemanager.controllers;

import hro.ictlab.nodemanager.database.DatabaseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/containers")
public class ContainerController {

    private DatabaseHandler databaseHandler = new DatabaseHandler();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainers() throws Exception {
        String output = databaseHandler.ContainerRequest(null);
        return Response.ok().entity(output).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newContainer(@Context HttpServletRequest request) throws  Exception{
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainerById(@PathParam("id") String id) throws Exception {
        String output = databaseHandler.ContainerRequest(id);
        return Response.ok().entity(output).build();
    }
}

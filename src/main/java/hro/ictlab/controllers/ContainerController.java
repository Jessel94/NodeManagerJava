package main.java.hro.ictlab.controllers;

import main.java.hro.ictlab.services.ContainerRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/docker")
public class ContainerController {

    private ContainerRepository containerRepository = new ContainerRepository();

    @GET
    @Path("/containers/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainers() throws IOException {
        return Response.ok().entity(containerRepository.getAllContainers().toString()).build();
    }

    @GET
    @Path("/containers/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainerById(@PathParam("id") String id) throws IOException {
        JSONArray jsonArray = containerRepository.getAllContainers();
        return Response.ok().entity(jsonArray.toString()).build();
    }
}

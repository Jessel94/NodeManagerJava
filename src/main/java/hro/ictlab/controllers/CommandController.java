package main.java.hro.ictlab.controllers;

import main.java.hro.ictlab.services.ContainerRepository;
import org.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/commands")
public class CommandController {

    private ContainerRepository containerRepository = new ContainerRepository();

    @GET
    @Path("/{id}/{command}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainerById(@PathParam("id") String id, @PathParam("command") String command) throws IOException {
        JSONArray jsonArray = containerRepository.getAllContainers();
        return Response.ok().entity(jsonArray.toString()).build();
    }
}

package hro.ictlab.nodemanager.controllers.Container;

import com.rabbitmq.client.Channel;
import hro.ictlab.nodemanager.connectors.DbConnector;
import hro.ictlab.nodemanager.connectors.RabbitConnector;
import hro.ictlab.nodemanager.database.DbHandler;
import hro.ictlab.nodemanager.helperclasses.PostDataFormatter;
import hro.ictlab.nodemanager.models.NewContainer;
import hro.ictlab.nodemanager.rabbitmq.RabbitHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.sql.Connection;

@Path("/containers/")
public class ContainerPostController {

    private final DbHandler dbHandler = new DbHandler();
    private final RabbitHandler rabbitHandler = new RabbitHandler();
    private final DbConnector dbConnector = new DbConnector();
    private final RabbitConnector rabbitConnector = new RabbitConnector();
    private final PostDataFormatter postDataFormatter = new PostDataFormatter();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newContainer(@Context HttpServletRequest request, @Context HttpServletResponse response) throws Exception {
        InputStream body = null;
        Connection dbConn = null;
        com.rabbitmq.client.Connection rabbitConn = null;
        Channel rabbitChannel = null;
        try {
            body = request.getInputStream();
            dbConn = dbConnector.getConnection();
            rabbitConn = rabbitConnector.getConnection();
            rabbitChannel = rabbitConnector.getChannel(rabbitConn);

            NewContainer requestModel = postDataFormatter.formatNewContainer(body);
            String queueId = dbHandler.nodeQueueID(requestModel.getNode(), dbConn);
            String containerId = dbHandler.newContainer(requestModel.getContainerName(), queueId, dbConn);
            rabbitHandler.processCommand(containerId, queueId, "create", rabbitChannel, requestModel.getHostPort(), requestModel.getContainerPort(), requestModel.getBaseImage());

            return Response.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            try {
                if (body != null) {
                    body.close();
                }
                dbConnector.closeConnection(dbConn);
                rabbitConnector.closeConnection(rabbitConn, rabbitChannel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

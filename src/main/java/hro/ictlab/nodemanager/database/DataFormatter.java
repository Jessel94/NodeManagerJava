package hro.ictlab.nodemanager.database;

import com.google.gson.Gson;
import hro.ictlab.nodemanager.models.Container;
import hro.ictlab.nodemanager.models.Queue;

import java.sql.ResultSet;
import java.util.ArrayList;

class DataFormatter {

    private Gson gson = new Gson();

    ArrayList ContainerFormatter(ResultSet rs) throws Exception
    {
        ArrayList messageData = new ArrayList();
        while(rs.next())
        {
            Container container = FillContainer(rs);
            messageData.add(container);
        }
        rs.close();
        return messageData;
    }

    Container FillContainer(ResultSet rs) throws Exception
    {
        Container container = new Container();
        container.setId(rs.getInt("id"));
        container.setQueueid(rs.getInt("queueid"));
        container.setName(rs.getString("name"));
        container.setCreationdate(rs.getString("creationdate"));
        container.setState(rs.getString("state"));
        return container;
    }

    Container ContainerData(ResultSet rs) throws Exception
    {
        if(rs.next()){
            Container container = FillContainer(rs);
            rs.close();
            return container;
        }
        else { return null; }
    }

    ArrayList QueueFormatter(ResultSet rs) throws Exception
    {
        ArrayList messageData = new ArrayList();
        while(rs.next())
        {
            Queue queue = FillQueue(rs);
            messageData.add(queue);
        }
        rs.close();
        return messageData;
    }

    Queue FillQueue(ResultSet rs) throws Exception
    {
        Queue queue = new Queue();
        queue.setId(rs.getInt("id"));
        queue.setHostname(rs.getString("hostname"));
        queue.setQueuename(rs.getString("queuename"));
        queue.setQueuepass(rs.getString("queuepass"));
        return queue;
    }

    Queue QueueData(ResultSet rs) throws Exception
    {
        if(rs.next()){
            Queue queue = new Queue();
            queue.setId(rs.getInt(1));
            rs.close();
            return queue;
        }
        else { return null; }
    }

    String GSONFormatter(ArrayList arrayList) {
        return gson.toJson(arrayList).toString();
    }
}

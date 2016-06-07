package hro.ictlab.nodemanager.database;

import com.google.gson.Gson;
import hro.ictlab.nodemanager.models.Container;
import hro.ictlab.nodemanager.models.Queue;

import java.sql.ResultSet;
import java.util.ArrayList;

class DataFormatter {

    private Gson gson = new Gson();

    ArrayList containerFormatter(ResultSet rs) throws Exception {
        ArrayList messageData = new ArrayList();
        while (rs.next()) {
            Container container = fillContainer(rs);
            messageData.add(container);
        }
        rs.close();
        return messageData;
    }

    private Container fillContainer(ResultSet rs) throws Exception {
        Container container = new Container();
        container.setId(rs.getInt("id"));
        container.setQueueid(rs.getInt("queueid"));
        container.setName(rs.getString("name"));
        container.setCreationdate(rs.getString("creationdate"));
        container.setState(rs.getString("state"));
        return container;
    }

    Container containerData(ResultSet rs) throws Exception {
        if (rs.next()) {
            Container container = fillContainer(rs);
            rs.close();
            return container;
        } else {
            return null;
        }
    }

    ArrayList queueFormatter(ResultSet rs) throws Exception {
        ArrayList messageData = new ArrayList();
        while (rs.next()) {
            Queue queue = fillQueue(rs);
            messageData.add(queue);
        }
        rs.close();
        return messageData;
    }

    private Queue fillQueue(ResultSet rs) throws Exception {
        Queue queue = new Queue();
        queue.setId(rs.getInt("id"));
        queue.setHostname(rs.getString("hostname"));
        queue.setQueuename(rs.getString("queuename"));
        queue.setQueuepass(rs.getString("queuepass"));
        return queue;
    }

    Queue queueData(ResultSet rs) throws Exception {
        if (rs.next()) {
            Queue queue = new Queue();
            queue.setId(rs.getInt(1));
            rs.close();
            return queue;
        } else {
            return null;
        }
    }

    String gsonFormatter(ArrayList arrayList) {
        return gson.toJson(arrayList).toString();
    }
}

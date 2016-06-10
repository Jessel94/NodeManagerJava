package hro.ictlab.nodemanager.database;

import com.google.gson.Gson;
import hro.ictlab.nodemanager.models.Container;
import hro.ictlab.nodemanager.models.Node;
import hro.ictlab.nodemanager.models.Queue;

import java.sql.ResultSet;
import java.util.ArrayList;

class DataFormatter {

    private final Gson gson = new Gson();

    ArrayList<Container> containerFormatter(ResultSet rs) throws Exception {
        ArrayList<Container> messageData = new ArrayList<>();
        while (rs.next()) {
            Container container = fillContainer(rs);
            messageData.add(container);
        }
        rs.close();
        return messageData;
    }

    ArrayList<Node> nodeFormatter(ResultSet rs) throws Exception {
        ArrayList<Node> messageData = new ArrayList<>();
        while (rs.next()) {
            Node node = fillNode(rs);
            messageData.add(node);
        }
        rs.close();
        return messageData;
    }

    private Container fillContainer(ResultSet rs) throws Exception {
        Container container = new Container();
        container.setId(rs.getInt("id"));
        container.setQueueid(rs.getInt("queueid"));
        container.setName(rs.getString("name"));
        container.setCreationdate(rs.getTimestamp("creationdate"));
        container.setState(rs.getString("state"));
        return container;
    }

    private Node fillNode(ResultSet rs) throws Exception {
        Node node = new Node();
        node.setId(rs.getInt("id"));
        node.setName(rs.getString("name"));
        node.setQueueid(rs.getInt("queueid"));
        node.setLastchecked(rs.getTimestamp("lastchecked"));
        return node;
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
        return gson.toJson(arrayList);
    }
}

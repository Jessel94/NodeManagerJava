package hro.ictlab.nodemanager.database;

import hro.ictlab.nodemanager.models.Container;

import java.sql.ResultSet;
import java.util.ArrayList;

class DataFormatter {

    ArrayList ContainerFormatter(ResultSet rs) throws Exception
    {
        ArrayList messageData = new ArrayList();
        while(rs.next())
        {
            Container container = new Container();
            container.setId(rs.getInt("id"));
            container.setQueueid(rs.getInt("queueid"));
            container.setName(rs.getString("name"));
            container.setCreationdate(rs.getString("creationdate"));
            container.setState(rs.getString("state"));
            messageData.add(container);
        }
        rs.close();
        return messageData;
    }
}

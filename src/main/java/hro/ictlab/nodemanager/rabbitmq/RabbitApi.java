package hro.ictlab.nodemanager.rabbitmq;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;


class RabbitApi {

    private final String rabbitMq = System.getenv("RABBITMQ");
    private final String rabbitMqPort = System.getenv("RABBITMQ_MANAGEMENT_PORT");
    private final String rabbitUser = System.getenv("RABBITMQ_USER");
    private final String rabbitPass = System.getenv("RABBITMQ_PASS");
    private final String encoding = new String(Base64.encodeBase64((rabbitUser + ":" + rabbitPass).getBytes()));

    void createUser(String userName, String passWord, CloseableHttpClient client) throws Exception {
        HttpPut putUser = new HttpPut("http://" + (rabbitMq + rabbitMqPort + "/api/users/" + userName));
        putUser.addHeader("Authorization", "Basic " + encoding); // RabbitMQ requires a user with create permission, create it manually first
        putUser.addHeader("content-type", "application/json");
        putUser.setEntity(new StringEntity("{\"password\":\"" + passWord + "\",\"tags\":\"management\"} "));
        client.execute(putUser);
    }

    void userPermissions(String userName, CloseableHttpClient client) throws Exception {
        HttpPut putPermissions = new HttpPut("http://" + (rabbitMq + rabbitMqPort + "/api/permissions/NodeManager/" + userName));
        putPermissions.addHeader("Authorization", "Basic " + encoding);
        putPermissions.addHeader("content-type", "application/json");
        putPermissions.setEntity(new StringEntity("{\"configure\":\"^$\",\"write\":\".*\",\"read\":\".*\"}")); // Permission you wanna. Check RabbitMQ HTTP API for details
        client.execute(putPermissions);
    }
}

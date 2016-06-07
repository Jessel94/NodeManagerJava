package hro.ictlab.nodemanager.services;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class RabbitMQ {

    public void createUser(String userName, String passWord) {
        String enc = new String(Base64.encodeBase64((System.getenv("RABBITMQ_USER") + ":" + System.getenv("RABBITMQ_PASS")).getBytes()));
        CloseableHttpClient client = HttpClientBuilder.create().build();

        try {
            HttpPut putUser = new HttpPut("http://" + (System.getenv("RABBITMQ") + System.getenv("RABBITMQ_MANAGEMENT_PORT") + "/api/users/" + userName));
            putUser.addHeader("Authorization", "Basic " + enc); // RabbitMQ requires a user with create permission, create it manually first
            putUser.addHeader("content-type", "application/json");
            putUser.setEntity(new StringEntity("{\"password\":\"" + passWord + "\",\"tags\":\"management\"} "));
            client.execute(putUser);

            //After create, configure RabbitMQ permission

            HttpPut putPermissions = new HttpPut("http://" + (System.getenv("RABBITMQ") + System.getenv("RABBITMQ_MANAGEMENT_PORT") + "/api/permissions/NodeManager/" + userName));
            putPermissions.addHeader("Authorization", "Basic " + enc);
            putPermissions.addHeader("content-type", "application/json");
            putPermissions.setEntity(new StringEntity("{\"configure\":\"^$\",\"write\":\".*\",\"read\":\".*\"}")); // Permission you wanna. Check RabbitMQ HTTP API for details
            client.execute(putPermissions);

        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

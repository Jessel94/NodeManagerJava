package main.java.hro.ictlab;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("resources")
public class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig() {
        packages(MyResourceConfig.class.getPackage().getName());
    }
}

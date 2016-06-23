package hro.ictlab.nodemanager.heartbeat;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class HeartBeatReader {

    String readHeartBeat(String ip) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL("http://" + ip + ":53452").openConnection();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (InputStream in = conn.getInputStream()) {
            byte[] buffer = new byte[2048];
            int read;
            while ((read = in.read(buffer)) > 0) {
                out.write(buffer, 0, read);
            }
        }
        return out.toString();
    }
}

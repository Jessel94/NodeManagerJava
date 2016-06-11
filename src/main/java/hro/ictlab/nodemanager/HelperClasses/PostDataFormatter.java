package hro.ictlab.nodemanager.helperclasses;

import com.google.gson.Gson;
import hro.ictlab.nodemanager.models.NewContainer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PostDataFormatter {

    private final Gson gson = new Gson();

    public NewContainer formatNewContainer(InputStream body) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(body));

        StringBuilder jb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            jb.append(line);

        return gson.fromJson(jb.toString(), NewContainer.class);
    }
}

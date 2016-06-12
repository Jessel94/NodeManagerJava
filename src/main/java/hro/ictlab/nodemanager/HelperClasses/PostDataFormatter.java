package hro.ictlab.nodemanager.helperclasses;

import com.google.gson.Gson;
import hro.ictlab.nodemanager.models.NewContainer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class that is used to format the post requests
 */
public class PostDataFormatter {

    private final Gson gson = new Gson();

    /**
     * Method that is used to handle the post requests for a new container
     *
     * @param body The raw inputStream that will be processed.
     * @return Returns a NewContainer model containing all the info from  the post
     */
    public NewContainer formatNewContainer(InputStream body) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(body));

        StringBuilder jb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            jb.append(line);

        return gson.fromJson(jb.toString(), NewContainer.class);
    }
}

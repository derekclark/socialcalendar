package uk.co.socialcalendar.user.persistence;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import uk.co.socialcalendar.user.entities.User;
import uk.co.tpplc.http.Response;
import uk.co.tpplc.http.SimpleHttpClient;

import java.io.IOException;

public class UserDAOWebServiceImpl {

    public static final String POST_URL = "http://localhost:9000/social/v1/user";

    public String toJson (Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(object);
    }

    public Response save(User user) {
        return postToService(user);
    }

    private Response postToService(User user) {
        try {
            return new SimpleHttpClient().post(POST_URL,
                    toJson(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package uk.co.socialcalendar.user.persistence;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import uk.co.socialcalendar.user.entities.User;
import uk.co.tpplc.http.Response;
import uk.co.tpplc.http.SimpleHttpClient;

import java.io.IOException;

public class UserDAOWebServiceImpl {
    public static final String URL = "http://localhost:9000/social/v1/user";

    public Response save(User user) {
        return postToService(user);
    }

    public Response read(String userId) {
        return getFromService(userId);
    }

    private Response getFromService(String userId) {
        return new SimpleHttpClient().get(URL+"/"+userId);
    }

    private Response postToService(User user) {
        try {
            return new SimpleHttpClient().post(URL,
                    toJson(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Response deleteFromService(String userId) {
        return new SimpleHttpClient().delete(URL+"/"+userId);
    }

    public String toJson (Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(object);
    }


    public Response delete(String userId) {
        return deleteFromService(userId);
    }
}

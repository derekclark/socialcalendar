package uk.co.socialcalendar.user.persistence;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import uk.co.socialcalendar.user.entities.User;
import uk.co.tpplc.http.Response;
import uk.co.tpplc.http.SimpleHttpClient;

import java.io.IOException;

public class UserDAOWebServiceImpl {
    ObjectMapper objectMapper;
    public static final String URL = "http://localhost:9000/social/v1/user";

    public UserDAOWebServiceImpl() {
        objectMapper = new ObjectMapper();
    }

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
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(object);
    }


    public Response delete(String userId) {
        return deleteFromService(userId);
    }

    public User convertJsonPayloadToUser(String jsonPayload) {
        try {
            return deserializeJsonToObject(jsonPayload,User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T deserializeJsonToObject(String jsonString, Class<T> clazz) throws IOException {
        return objectMapper.readValue(jsonString, clazz);
    }

}

package uk.co.socialcalendar.user.persistence;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import uk.co.tpplc.http.Response;
import uk.co.tpplc.http.SimpleHttpClient;

import java.io.IOException;

public class WebServiceAdapter {
    String entityUrl;
    public static final String BASE_URL = "http://localhost:9000/social/v1/";


    public WebServiceAdapter(String entity) {
        this.entityUrl = entity;
    }


    public <T> Response save(T clazz) {
        return postToService(clazz);
    }

    public Response read(String userId) {
        return getFromService(userId);
    }

    public Response delete(String userId) {
        return deleteFromService(userId);
    }

    private Response getFromService(String entityId) {
        return new SimpleHttpClient().get(BASE_URL + entityUrl + "/"+entityId);
    }

    private <T> Response postToService(T clazz) {
        try {
            return new SimpleHttpClient().post(BASE_URL + entityUrl,
                    toJson(clazz));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Response deleteFromService(String userId) {
        return new SimpleHttpClient().delete(BASE_URL + entityUrl + "/"+userId);
    }

    public String toJson (Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(object);
    }
}

package uk.co.socialcalendar.user.persistence;

import org.codehaus.jackson.map.ObjectMapper;
import uk.co.socialcalendar.user.entities.User;
import uk.co.tpplc.http.Response;

import java.io.IOException;

public class UserDAOWebServiceImpl implements UserDAO{
    UserDAOWebServiceAdapter adapter;

    public UserDAOWebServiceImpl() {
        adapter = new UserDAOWebServiceAdapter();
    }

    @Override
    public User read(String userEmail) {
        Response response = adapter.read(userEmail);
        return convertJsonPayloadToUser(response.getBody());
    }

    @Override
    public boolean save(User user) {
        if (user.getEmail().isEmpty()){
            return false;
        }
        adapter.save(user);
        return true;
    }

    @Override
    public UserHibernateModel getUserModel(String userEmail) {
        return null;
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
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, clazz);
    }

}

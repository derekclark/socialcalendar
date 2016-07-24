package user.persistence;

import org.junit.Test;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserDAOWebServiceImpl;
import uk.co.tpplc.http.Response;

import static org.junit.Assert.assertEquals;

public class UserDAOWebServiceImplTests {
    public static final String EMAIL = "decla";
    public static final String NAME = "derek";
    public static final String FACEBOOK_ID = "1234";

    User user;
    UserDAOWebServiceImpl userDAO = new UserDAOWebServiceImpl();;

    @Test
    public void get200ResponseWhenAddingNewUser(){
        User user = new User("NewUserId", NAME, FACEBOOK_ID);
        Response response = userDAO.save(user);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void get200ResponseWhenReadingExistingUser(){
        User user = new User(EMAIL, NAME, FACEBOOK_ID);
        Response response = userDAO.save(user);
        assertEquals(200, response.getStatus());

        response = userDAO.read(EMAIL);
        assertEquals(200, response.getStatus());
    }


}

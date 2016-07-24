package user.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.WebServiceAdapter;
import uk.co.socialcalendar.user.persistence.UserDAOWebServiceImpl;

import static org.junit.Assert.*;

public class UserDAOWebServiceImplTests {
    public static final String EMAIL = "decla";
    public static final String NAME = "derek";
    public static final String FACEBOOK_ID = "1234";
    public static final String NEW_USER_ID = "NewUserId";

    UserDAOWebServiceImpl userDAO = new UserDAOWebServiceImpl();
    User user;
    WebServiceAdapter adapter = new WebServiceAdapter("user");

    @Before
    public void setup(){
        user = new User(EMAIL, NAME, FACEBOOK_ID);
    }

    @After
    public void tearDown(){
        adapter.delete(EMAIL);
    }

    @Test
    public void returnsTrueIfAddingUserWithId(){
        assertTrue(userDAO.save(user));
    }

    @Test
    public void returnsFalseIfAddingUserWithEmptyId(){
        User user = new User("", NAME, FACEBOOK_ID);
        assertFalse(userDAO.save(user));
    }

    @Test
    public void canConvertResponseBodyIntoUser(){
        String jsonPayload = "{\n" +
                "  \"email\" : \""+EMAIL+"\",\n" +
                "  \"name\" : \""+NAME+"\",\n" +
                "  \"facebookId\" : \""+FACEBOOK_ID+"\"\n" +
                "}";

        User actualUser = userDAO.convertJsonPayloadToUser(jsonPayload);
        User expectedUser = new User(EMAIL, NAME, FACEBOOK_ID);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void canRetrieveSavedUser(){
        userDAO.save(user);
        User retrievedUser = userDAO.read(user.getEmail());
        assertEquals(user, retrievedUser);
    }

}

package user.persistence;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserDAOWebServiceAdapter;
import uk.co.tpplc.http.Response;

import static org.junit.Assert.assertEquals;

public class UserDAOWebServiceAdapterTests {
    public static final String EMAIL = "decla";
    public static final String NAME = "derek";
    public static final String FACEBOOK_ID = "1234";
    public static final String NEW_USER_ID = "NewUserId";

    User actualUser = new User(EMAIL, NAME, FACEBOOK_ID);
    UserDAOWebServiceAdapter userDAO = new UserDAOWebServiceAdapter();;

    @Before
    public void setup(){
        deleteUser(EMAIL);
        userDAO.save(actualUser);
    }

    @After
    public void tearDown(){
        deleteUser(EMAIL);
    }

    private Response deleteUser(String userId){
        return userDAO.delete(userId);
    }

    @Test
    public void get200ResponseWhenAddingNewUser(){
        deleteUser(NEW_USER_ID);

        User user = new User(NEW_USER_ID, NAME, FACEBOOK_ID);
        Response response = userDAO.save(user);
        assertEquals(200, response.getStatus());

        response = deleteUser(NEW_USER_ID);
        assertEquals(200,response.getStatus());
    }

    @Test
    public void get200ResponseWhenReadingExistingUser(){
        Response response = userDAO.read(EMAIL);
        assertEquals(200, response.getStatus());
    }


    @Test
    public void returnsUserWhenReadingExistingUser(){

    }

    @Test
    public void get404ResponseWhenReadingNonExistingUser(){
        Response response = userDAO.read("UNKNOWN_USER_ID");
        assertEquals(404, response.getStatus());
    }

    @Test
    public void get200ResponseWhenDeletingExistingUser(){
        Response response = userDAO.delete(EMAIL);
        assertEquals(200, response.getStatus());
    }
}

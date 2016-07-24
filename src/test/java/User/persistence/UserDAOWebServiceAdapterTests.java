package user.persistence;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.WebServiceAdapter;
import uk.co.tpplc.http.Response;

import static org.junit.Assert.assertEquals;

public class UserDAOWebServiceAdapterTests {
    public static final String EMAIL = "decla";
    public static final String NAME = "derek";
    public static final String FACEBOOK_ID = "1234";
    public static final String NEW_USER_ID = "NewUserId";

    User actualUser = new User(EMAIL, NAME, FACEBOOK_ID);
    WebServiceAdapter userAdapter = new WebServiceAdapter("user");

    @Before
    public void setup(){
        deleteUser(EMAIL);
        userAdapter.save(actualUser);
    }

    @After
    public void tearDown(){
        deleteUser(EMAIL);
    }

    private Response deleteUser(String userId){
        return userAdapter.delete(userId);
    }

    @Test
    public void get200ResponseWhenAddingNewUser(){
        deleteUser(NEW_USER_ID);

        User user = new User(NEW_USER_ID, NAME, FACEBOOK_ID);
        Response response = userAdapter.save(user);
        assertEquals(200, response.getStatus());

        response = deleteUser(NEW_USER_ID);
        assertEquals(200,response.getStatus());
    }

    @Test
    public void get200ResponseWhenReadingExistingUser(){
        Response response = userAdapter.read(EMAIL);
        assertEquals(200, response.getStatus());
    }


    @Test
    public void returnsUserWhenReadingExistingUser(){

    }

    @Test
    public void get404ResponseWhenReadingNonExistingUser(){
        Response response = userAdapter.read("UNKNOWN_USER_ID");
        assertEquals(404, response.getStatus());
    }

    @Test
    public void get200ResponseWhenDeletingExistingUser(){
        Response response = userAdapter.delete(EMAIL);
        assertEquals(200, response.getStatus());
    }
}

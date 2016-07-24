package user.persistence;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserDAOWebServiceImpl;
import uk.co.tpplc.http.Response;

import static org.junit.Assert.assertEquals;

public class UserDAOWebServiceImplTests {
    public static final String EMAIL = "decla";
    public static final String NAME = "derek";
    public static final String FACEBOOK_ID = "1234";

    User user = new User(EMAIL, NAME, FACEBOOK_ID);
    UserDAOWebServiceImpl userDAO = new UserDAOWebServiceImpl();;

    @Before
    public void setup(){
        deleteUser(EMAIL);
        userDAO.save(user);
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
        deleteUser("NewUserId");

        User user = new User("NewUserId", NAME, FACEBOOK_ID);
        Response response = userDAO.save(user);
        assertEquals(200, response.getStatus());

        response = deleteUser("NewUserId");
        assertEquals(200,response.getStatus());
    }

    @Test
    public void get200ResponseWhenReadingExistingUser(){
        Response response = userDAO.read(EMAIL);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void get200ResponseWhenDeletingExistingUser(){
        Response response = userDAO.delete(EMAIL);
        assertEquals(200, response.getStatus());
    }
}

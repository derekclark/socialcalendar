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

    @Test
    public void get200ResponseWhenSavingUser(){
        UserDAOWebServiceImpl userDAO = new UserDAOWebServiceImpl();
        User user = new User(EMAIL, NAME, FACEBOOK_ID);
        Response response = userDAO.save(user);

        assertEquals(200, response.getStatus());
    }
}

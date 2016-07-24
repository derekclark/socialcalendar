package user.persistence;

import org.junit.Test;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserDAOWebServiceImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserDAOWebServiceImplTests {
    public static final String EMAIL = "decla";
    public static final String NAME = "derek";
    public static final String FACEBOOK_ID = "1234";
    public static final String NEW_USER_ID = "NewUserId";

    UserDAOWebServiceImpl userDAO = new UserDAOWebServiceImpl();

    @Test
    public void canSaveUser(){
        User user = new User(EMAIL, NAME, FACEBOOK_ID);
        assertTrue(userDAO.save(user));
    }

    @Test
    public void returnsFalseIfAddingUserWithEmptyId(){
        User user = new User("", NAME, FACEBOOK_ID);
        assertFalse(userDAO.save(user));
    }

}

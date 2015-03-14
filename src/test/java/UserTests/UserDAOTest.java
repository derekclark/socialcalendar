package UserTests;

import org.junit.Test;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.frameworksAndDrivers.persistence.InMemoryUserDAO;
import uk.co.socialcalendar.useCases.user.UserDAO;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserDAOTest {

    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String FACEBOOKID = "facebookid";
    UserDAO userDAO = new InMemoryUserDAO();

    @Test
    public void canCreateInstance(){
        assertTrue(userDAO instanceof UserDAO);
    }

    @Test
    public void canSaveUser(){
        User user = new User(EMAIL, NAME, FACEBOOKID);
        assertTrue(userDAO.save(user));
    }
    @Test
    public void cadReadUser(){
        User user = new User(EMAIL, NAME, FACEBOOKID);
        userDAO.save(user);
        assertEquals(user, userDAO.read(EMAIL));
    }
}

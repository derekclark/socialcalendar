package UserTests;

import org.junit.Test;
import uk.co.socialcalendar.frameworksAndDrivers.InMemoryUserDAO;
import uk.co.socialcalendar.useCases.UserDAO;

import static org.junit.Assert.assertTrue;

public class UserDAOTest {

    UserDAO userDAO = new InMemoryUserDAO();

    @Test
    public void canCreateInstance(){
        assertTrue(userDAO instanceof UserDAO);
    }
}

package UserTests;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.useCases.UserDAO;
import uk.co.socialcalendar.useCases.UserFacadeImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserFacadeImplTest {


    UserFacadeImpl facade;


    UserDAO mockUserDAO = mock(UserDAO.class);

    private final static String USER_EMAIL = "userEmail";
    private final static String USER_NAME = "userName";
    private final static String FACEBOOK_ID = "facebookId";

    @Before
    public void setup(){
        facade = new UserFacadeImpl();
        facade.setUserDAO(mockUserDAO);

    }

    @Test
    public void getUser(){
        User expectedUser = new User();
        expectedUser.setEmail(USER_EMAIL);
        expectedUser.setFacebookId(FACEBOOK_ID);
        expectedUser.setName(USER_NAME);

        when(mockUserDAO.getUser(USER_EMAIL)).thenReturn(expectedUser);

        User actualUser = facade.getUser(USER_EMAIL);

        assertEquals(expectedUser,actualUser);
    }
}

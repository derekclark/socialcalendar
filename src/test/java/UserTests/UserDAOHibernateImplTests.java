package UserTests;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.frameworksAndDrivers.UserDAOHibernateImpl;
import uk.co.socialcalendar.frameworksAndDrivers.UserHibernateModel;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class UserDAOHibernateImplTests {

    public static final String NAME = "name";
    public static final String FACEBOOK_ID = "facebookId";
    UserDAOHibernateImpl userDAOImpl;
    User user;
    SessionFactory mockSessionFactory;
    Session mockSession;
    Query mockQuery;

    private final static int USER_ID = 1;
    private final static String EMAIL = "email";

    @Before
    public void setup(){
        userDAOImpl = new UserDAOHibernateImpl();
        user = new User(EMAIL, NAME, FACEBOOK_ID);
        setupMocks();
    }

    public void setupMocks(){
        mockSessionFactory = mock(SessionFactory.class);
        mockSession = mock(Session.class);
        mockQuery = mock(Query.class);
        userDAOImpl.setSessionFactory(mockSessionFactory);
        when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
    }

    @Test
    public void canGetUser(){
        UserHibernateModel userHibernateModel = getUserHibernateModel();
        when(mockSession.get(UserHibernateModel.class, EMAIL)).thenReturn(userHibernateModel);
        User actualUser = userDAOImpl.read(EMAIL);
        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(user.getName(), actualUser.getName());
        assertEquals(user.getFacebookId(), actualUser.getFacebookId());
    }

    @Test
    public void returnsNullWhenUserNotFound(){
        UserHibernateModel userHibernateModel = getUserHibernateModel();
        when(mockSession.get(UserHibernateModel.class, EMAIL)).thenReturn(null);
        User actualUser = userDAOImpl.read(EMAIL);
        assertNull(actualUser);
    }

    @Test
    public void canSaveUser(){
        assertTrue(userDAOImpl.save(user));
    }

    @Test
    public void saveCallsSessionSave(){
        userDAOImpl.save(user);
        verify(mockSession).save(anyObject());
    }

    @Test
    public void convertUserHibernateModelToUser(){
        UserHibernateModel userHibernateModel = getUserHibernateModel();
        User expectedUser = new User(EMAIL, NAME, FACEBOOK_ID);
        User actualUser = userDAOImpl.convertToUser(userHibernateModel);
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getFacebookId(), actualUser.getFacebookId());
        assertEquals(expectedUser.getName(), actualUser.getName());
    }

    @Test
    public void convertUserToUserHibernateModel(){
        UserHibernateModel expectedUserHibernateModel = getUserHibernateModel();
        UserHibernateModel actualUserHibernateModel = userDAOImpl.convertToUserHibernateModel(user);

        assertEquals(expectedUserHibernateModel.getEmail(), actualUserHibernateModel.getEmail());
        assertEquals(expectedUserHibernateModel.getFacebookId(), actualUserHibernateModel.getFacebookId());
        assertEquals(expectedUserHibernateModel.getName(), actualUserHibernateModel.getName());

    }

    private UserHibernateModel getUserHibernateModel() {
        UserHibernateModel userHibernateModel = new UserHibernateModel(user);
        userHibernateModel.setEmail(EMAIL);
        userHibernateModel.setName(NAME);
        userHibernateModel.setFacebookId(FACEBOOK_ID);
        return userHibernateModel;
    }
}

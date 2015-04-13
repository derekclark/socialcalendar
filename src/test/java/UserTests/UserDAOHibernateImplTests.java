package UserTests;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.frameworksAndDrivers.persistence.UserDAOHibernateImpl;
import uk.co.socialcalendar.frameworksAndDrivers.persistence.UserHibernateModel;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.internal.matchers.Equality.areEqual;

public class UserDAOHibernateImplTests {
    UserDAOHibernateImpl userDAOImpl;
    User user;
    SessionFactory mockSessionFactory;
    Session mockSession;
    Query mockQuery;
    public static final String NAME = "name";
    public static final String FACEBOOK_ID = "facebookId";
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
        UserHibernateModel userHibernateModel = new UserHibernateModel(user);
        when(mockSession.get(UserHibernateModel.class, EMAIL)).thenReturn(userHibernateModel);
        User actualUser = userDAOImpl.read(EMAIL);
        areEqual(user, actualUser);
    }

    @Test
    public void returnsNullWhenUserNotFound(){
        UserHibernateModel userHibernateModel = new UserHibernateModel(user);
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
        UserHibernateModel userHibernateModel = new UserHibernateModel(user);
        User expectedUser = new User(EMAIL, NAME, FACEBOOK_ID);
        User actualUser = userDAOImpl.convertToUser(userHibernateModel);
        areEqual(expectedUser, actualUser);
    }

    @Test
    public void convertUserToUserHibernateModel(){
        UserHibernateModel expectedUserHibernateModel = new UserHibernateModel(user);
        UserHibernateModel actualUserHibernateModel = userDAOImpl.convertToUserHibernateModel(user);
        areEqual(expectedUserHibernateModel, actualUserHibernateModel);
    }
}

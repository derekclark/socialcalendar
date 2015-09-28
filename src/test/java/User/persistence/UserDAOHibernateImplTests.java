package user.persistence;

import testSupport.InMemoryHibernateDB;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserDAOHibernateImpl;
import uk.co.socialcalendar.user.persistence.UserHibernateModel;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDAOHibernateImplTests {
    UserDAOHibernateImpl userDAOImpl;
    User user;
    SessionFactory mockSessionFactory;
    Session testSession;
    public static final String NAME = "name";
    public static final String FACEBOOK_ID = "facebookId";
    private final static String ME = "email";

    @Before
    public void setup(){
        userDAOImpl = new UserDAOHibernateImpl();
        user = new User(ME, NAME, FACEBOOK_ID);
        setupTestDatabase();
    }

    @After
    public void teardown(){
        Transaction t = testSession.getTransaction();
        t.rollback();
    }

//    @AfterClass
//    public static void classTearDown(){
//        HibernateUtil.shutdown();
//    }

    public void setupTestDatabase(){
        getHibernateTestInstance();
        mockSessionFactory = mock(SessionFactory.class);
        userDAOImpl.setSessionFactory(mockSessionFactory);
        when (mockSessionFactory.getCurrentSession()).thenReturn(testSession);
    }

    public void getHibernateTestInstance(){
        testSession = InMemoryHibernateDB.getSessionFactory().openSession();
        testSession.beginTransaction();
    }

    @Test
    public void canSaveUser(){
        assertTrue(userDAOImpl.save(user));
    }

    @Test
    public void canGetUser(){
        UserHibernateModel userHibernateModel = new UserHibernateModel(user);
        userDAOImpl.save(user);
        User actualUser = userDAOImpl.read(ME);
        assertEquals(user, actualUser);
    }

    @Test
    public void returnsNullWhenUserNotFound(){
        UserHibernateModel userHibernateModel = new UserHibernateModel(user);
        User actualUser = userDAOImpl.read(ME);
        assertNull(actualUser);
    }


    @Test
    public void convertUserHibernateModelToUser(){
        UserHibernateModel userHibernateModel = new UserHibernateModel(user);
        User expectedUser = new User(ME, NAME, FACEBOOK_ID);
        User actualUser = userDAOImpl.convertToUser(userHibernateModel);
        assertEquals(expectedUser,actualUser);
    }

    @Test
    public void convertUserToUserHibernateModel(){
        UserHibernateModel expectedUserHibernateModel = new UserHibernateModel(user);
        UserHibernateModel actualUserHibernateModel = userDAOImpl.convertToUserHibernateModel(user);
        assertEquals(expectedUserHibernateModel, actualUserHibernateModel);
    }

    @Test
    public void returnsUserHibernateModel(){
        UserHibernateModel expectedModel = new UserHibernateModel(user);

        userDAOImpl.save(user);
        UserHibernateModel actualModel = userDAOImpl.getUserModel(ME);
        assertEquals(expectedModel, actualModel);
    }

}

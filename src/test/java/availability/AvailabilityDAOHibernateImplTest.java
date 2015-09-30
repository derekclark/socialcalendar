package availability;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testSupport.InMemoryHibernateDB;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.AvailabilityDAOHibernateImpl;
import uk.co.socialcalendar.availability.persistence.AvailabilityHibernateModel;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserDAOHibernateImpl;
import uk.co.socialcalendar.user.persistence.UserHibernateModel;

import java.util.*;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AvailabilityDAOHibernateImplTest {

    public static final int FAILED_TO_INSERT_RECORD = -1;
    public static final int NON_EXISTENT_ID = 9999999;
    AvailabilityDAOHibernateImpl availabilityDAOImpl;
    Availability availability;
    UserDAOHibernateImpl userDAOImpl;
    Session testSession;
    SessionFactory mockSessionFactory;
    User user1 = new User("USER1","NAME1","FACEBOOK1");
    User user2 = new User("USER2","NAME2","FACEBOOK2");
    LocalDateTime startDate = new LocalDateTime();
    LocalDateTime endDate = new LocalDateTime();
    @Before
    public void setup(){
        availabilityDAOImpl = new AvailabilityDAOHibernateImpl();
        userDAOImpl = new UserDAOHibernateImpl();
        availabilityDAOImpl.setUserDAO(userDAOImpl);

        availability = new Availability("ownerEmail", "ownerName", "title", startDate, endDate, "status",
                add2SharedUsers());
        setupTestDatabase();
    }

    @After
    public void teardown(){
        Transaction t = testSession.getTransaction();
        t.rollback();
    }

    @Test
    public void canConvertAvailabilityToHibernateModel(){
        userDAOImpl.save(user1);
        userDAOImpl.save(user2);

        AvailabilityHibernateModel expectedModel = new AvailabilityHibernateModel("ownerEmail", "ownerName",
                "title", startDate, endDate, "status",
                add2SharedUsers());

        AvailabilityHibernateModel actualModel = availabilityDAOImpl.convertToHibernateModel(availability);
        assertThat(expectedModel, is(actualModel));
    }

    @Test
    public void canSaveAvailability(){
        assertThat(availabilityDAOImpl.save(availability), greaterThan(0));
    }


    @Test
    public void doesNotSaveAvailabilityWithEmptyOwnerEmail(){
        availability = new Availability("", "ownerName", "title", new LocalDateTime(), new LocalDateTime(), "status");
        assertThat(availabilityDAOImpl.save(availability), is(FAILED_TO_INSERT_RECORD));
    }

    @Test
    public void doesNotSaveAvailabilityWithEmptyOwnerName(){
        availability = new Availability("ownerEmail", "", "title", new LocalDateTime(), new LocalDateTime(), "status");
        assertThat(availabilityDAOImpl.save(availability), is(FAILED_TO_INSERT_RECORD));
    }

    @Test
    public void doesSaveAvailabilityWithEmptyTitle(){
        availability = new Availability("ownerEmail", "ownerName", "", new LocalDateTime(), new LocalDateTime(), "status",
                add2SharedUsers());
        AvailabilityHibernateModel availabilityHibernateModel = convertToHibernateModel(availability);
        assertThat(availabilityDAOImpl.save(availability), greaterThan(0));
    }

    @Test
    public void doesNotSaveAvailabilityWithEmptyStatus(){
        availability = new Availability("ownerEmail", "ownerName", "title", new LocalDateTime(), new LocalDateTime(), "");
        assertThat(availabilityDAOImpl.save(availability), is(FAILED_TO_INSERT_RECORD));
    }

    @Test
    public void doesNotSaveAvailabilityWithEmptyStartDate(){
        availability = new Availability("ownerEmail", "ownerName", "title", null, new LocalDateTime(), "status");
        assertThat(availabilityDAOImpl.save(availability), is(FAILED_TO_INSERT_RECORD));
    }

    @Test
    public void doesNotSaveAvailabilityWithEmptyEndDate(){
        availability = new Availability("ownerEmail", "ownerName", "title", new LocalDateTime(), null, "status");
        assertThat(availabilityDAOImpl.save(availability), is(FAILED_TO_INSERT_RECORD));
    }

    @Test
    public void canReadOneAvailability(){
        Availability availability = new Availability("ownerEmail", "ownerName", "title", startDate, endDate, "status");

        availability.setId(availabilityDAOImpl.save(availability));
        Availability actualAvailability = availabilityDAOImpl.read(availability.getId());
        assertEquals(availability, actualAvailability);
    }

    @Test
    public void canReadAvailabilityWithSharedUsers(){
        userDAOImpl.save(user1);
        userDAOImpl.save(user2);
        Availability availability = createAvailabilityWithSharedUsers("me");

        availability.setId(availabilityDAOImpl.save(availability));
        Availability actualAvailability = availabilityDAOImpl.read(availability.getId());
        assertEquals(availability, actualAvailability);
        for(User user:availability.getSharedList()){
            System.out.println(user.getName());
        }

    }

    @Test
    public void CanSaveTwoAvailabilities(){
        Availability availability1 = new Availability("ownerEmail", "ownerName", "title", new LocalDateTime(), new LocalDateTime(), "status",
                addNoSharedUsers());
        Availability availability2 = new Availability("ownerEmail", "ownerName", "title", new LocalDateTime(), new LocalDateTime(), "status",
                addNoSharedUsers());
        availability1.setId(availabilityDAOImpl.save(availability1));
        availability2.setId(availabilityDAOImpl.save(availability2));

        assertEquals(availability1,availabilityDAOImpl.read(availability1.getId()));
        assertEquals(availability2,availabilityDAOImpl.read(availability2.getId()));
    }

    @Test
    public void canUpdateAvailability(){
        userDAOImpl.save(user1);
        userDAOImpl.save(user2);

        availability.setId(availabilityDAOImpl.save(availability));
        availability.setTitle("changedTitle");
        assertTrue(availabilityDAOImpl.update(availability));
        Availability actualAvailability = availabilityDAOImpl.read(availability.getId());
        assertEquals("changedTitle", actualAvailability.getTitle());
    }

    @Test
    public void doesNotUpdateIfRecordDoesNotExist(){
        availability.setId(NON_EXISTENT_ID);
        assertFalse(availabilityDAOImpl.update(availability));
    }

    @Test
    public void returnsNullIfIdDoesNotExist(){
        assertNull(availabilityDAOImpl.read(NON_EXISTENT_ID));
    }

    @Test
    public void returnsNoAvailabilitiesIfOwnerHasNone(){
        List<Availability> emptyList = new ArrayList<Availability>();
        assertEquals(emptyList, availabilityDAOImpl.readAllOwnersOpenAvailabilities("me"));
    }

    @Test
    public void returnsMyAvailabilitiesWhichIOwn(){
        userDAOImpl.save(user1);
        userDAOImpl.save(user2);
        List<Availability> expectedAvailabilities = new ArrayList<Availability>();
        Availability availability1 = createAvailabilityWithSharedUsers("me");
        expectedAvailabilities.add(availability1);
        Availability availability2 = createAvailabilityWithSharedUsers("me");
        expectedAvailabilities.add(availability2);
        Availability availability3 = createAvailabilityWithSharedUsers("another");
        Availability availability4 = createAvailabilityWithSharedUsers("another");

        List<Availability> availabilityList = new ArrayList<Availability>();
        availabilityList.add(availability1);
        availabilityList.add(availability2);
        availabilityList.add(availability3);
        availabilityList.add(availability4);
        persistAvailabilities(availabilityList);

        assertEquals(expectedAvailabilities, availabilityDAOImpl.readAllOwnersOpenAvailabilities("me"));
    }

    public void persistAvailabilities(List<Availability> availabilityList){
        for (Availability avail:availabilityList){
            avail.setId(availabilityDAOImpl.save(avail));
        }
    }

    public Availability createAvailabilityWithSharedUsers(String owner){
        return new Availability(owner, "Name","title1",new LocalDateTime(), new LocalDateTime(),"status",
                add2SharedUsers());
    }

    public Availability createAvailabilityWithNoSharedUsers(String owner){
        return new Availability(owner, "Name","title1",new LocalDateTime(), new LocalDateTime(),"status",
                addNoSharedUsers());
    }

    public void setupTestDatabase(){
        getHibernateTestInstance();
        mockSessionFactory = mock(SessionFactory.class);
        availabilityDAOImpl.setSessionFactory(mockSessionFactory);
        userDAOImpl.setSessionFactory(mockSessionFactory);
        when(mockSessionFactory.getCurrentSession()).thenReturn(testSession);
    }

    public void getHibernateTestInstance(){
        testSession = InMemoryHibernateDB.getSessionFactory().openSession();
        testSession.beginTransaction();
    }

    public AvailabilityHibernateModel convertToHibernateModel(Availability availability){
        AvailabilityHibernateModel availabilityHibernateModel = new AvailabilityHibernateModel();
        availabilityHibernateModel.setOwnerEmail(availability.getOwnerEmail());
        availabilityHibernateModel.setTitle(availability.getTitle());
        availabilityHibernateModel.setEndDate(availability.getEndDate());
        availabilityHibernateModel.setStartDate(availability.getStartDate());
        availabilityHibernateModel.setOwnerName(availability.getOwnerName());
        availabilityHibernateModel.setStatus(availability.getStatus());
        Set<User> sharedList = availability.getSharedList();
        Iterator<User> iterator = sharedList.iterator();
        while(iterator.hasNext()) {
            User setElement = iterator.next();
            UserHibernateModel userHibernateModel = new UserHibernateModel(setElement);
            availabilityHibernateModel.getSharedList().add(userHibernateModel);
        }
        return availabilityHibernateModel;
    }

    public Set<User> add2SharedUsers(){
        Set<User> sharedList = new HashSet<User>();
        sharedList.add(user1);
        sharedList.add(user2);
        return sharedList;
    }

    public Set<User> addNoSharedUsers(){
        Set<User> sharedList = new HashSet<User>();
        return sharedList;
    }
}

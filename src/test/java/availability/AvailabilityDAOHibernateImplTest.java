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

import java.util.ArrayList;
import java.util.List;

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
    AvailabilityDAOHibernateImpl availabilityDAOImpl;
    Availability availability;
    Session testSession;
    SessionFactory mockSessionFactory;
    public static final int NON_EXISTENT_ID = 9999999;

    @Before
    public void setup(){
        availabilityDAOImpl = new AvailabilityDAOHibernateImpl();
        availability = new Availability("ownerEmail", "ownerName", "title", new LocalDateTime(), new LocalDateTime(), "status");
        setupTestDatabase();
    }

    @After
    public void teardown(){
        Transaction t = testSession.getTransaction();
        t.rollback();
    }

    public void setupTestDatabase(){
        getHibernateTestInstance();
        mockSessionFactory = mock(SessionFactory.class);
        availabilityDAOImpl.setSessionFactory(mockSessionFactory);
        when (mockSessionFactory.getCurrentSession()).thenReturn(testSession);
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
        return availabilityHibernateModel;
    }

    @Test
    public void canConvertAvailabilityToHibernateModel(){
        AvailabilityHibernateModel expectedModel = convertToHibernateModel(availability);
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
        availability = new Availability("ownerEmail", "ownerName", "", new LocalDateTime(), new LocalDateTime(), "status");
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
        availability.setId(availabilityDAOImpl.save(availability));
        System.out.println(availability.getId());
        Availability actualAvailability = availabilityDAOImpl.read(availability.getId());
        assertEquals(availability, actualAvailability);
    }

    @Test
    public void CanSaveTwoAvailabilities(){
        Availability availability1 = new Availability("ownerEmail", "ownerName", "title", new LocalDateTime(), new LocalDateTime(), "status");
        Availability availability2 = new Availability("ownerEmail", "ownerName", "title", new LocalDateTime(), new LocalDateTime(), "status");
        availability1.setId(availabilityDAOImpl.save(availability1));
        availability2.setId(availabilityDAOImpl.save(availability2));

        assertEquals(availability1,availabilityDAOImpl.read(availability1.getId()));
        assertEquals(availability2,availabilityDAOImpl.read(availability2.getId()));
    }

    @Test
    public void canUpdateAvailability(){
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
        List<Availability> expectedAvailabilities = create2AvailabilitiesIOwn();
        persistAvailabilities(expectedAvailabilities);
        persistAvailabilities(create2AvailabilitiesIDontOwn());
        assertEquals(expectedAvailabilities, availabilityDAOImpl.readAllOwnersOpenAvailabilities("me"));
    }

    public void persistAvailabilities(List<Availability> availabilityList){
        for (Availability avail:availabilityList){
            avail.setId(availabilityDAOImpl.save(avail));
        }
    }

    public List<Availability> create2AvailabilitiesIOwn(){
        Availability availability1 = new Availability("me", "myName","title1",new LocalDateTime(), new LocalDateTime(),"status");
        Availability availability2 = new Availability("me", "myName","title2",new LocalDateTime(), new LocalDateTime(),"status");
        List<Availability> expectedAvailabilities = new ArrayList<Availability>();
        expectedAvailabilities.add(availability1);
        expectedAvailabilities.add(availability2);
        return expectedAvailabilities;
    }

    public List<Availability> create2AvailabilitiesIDontOwn(){
        Availability availability1 = new Availability("another", "myName","title1",new LocalDateTime(), new LocalDateTime(),"status");
        Availability availability2 = new Availability("another", "myName","title2",new LocalDateTime(), new LocalDateTime(),"status");
        List<Availability> expectedAvailabilities = new ArrayList<Availability>();
        expectedAvailabilities.add(availability1);
        expectedAvailabilities.add(availability2);
        return expectedAvailabilities;
    }

}

package availability;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.AvailabilityDAOHibernateImpl;
import uk.co.socialcalendar.availability.persistence.AvailabilityHibernateModel;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AvailabilityDAOHibernateImplTest {

    public static final int FAILED_TO_CREATE = -1;
    AvailabilityDAOHibernateImpl availabilityDAOImpl;
    Availability availability;
    Session mockSession;
    SessionFactory mockSessionFactory;

    @Before
    public void setup(){
        availabilityDAOImpl = new AvailabilityDAOHibernateImpl();
        availability = new Availability("ownerEmail", "ownerName", "title", new LocalDateTime(), new LocalDateTime(), "status");
        setupMocks();
    }

    public void setupMocks(){
        mockSession = mock(Session.class);
        mockSessionFactory = mock(SessionFactory.class);
        availabilityDAOImpl.setSessionFactory(mockSessionFactory);
        when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
        AvailabilityHibernateModel availabilityHibernateModel = convertToHibernateModel(availability);
        when(mockSession.save(availabilityHibernateModel)).thenReturn(1);
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
    public void canCreateInstance(){
        assertTrue(availabilityDAOImpl instanceof AvailabilityDAOHibernateImpl);
    }

    @Test
    public void canConvertAvailabilityToHibernateModel(){
        AvailabilityHibernateModel expectedModel = convertToHibernateModel(availability);
        AvailabilityHibernateModel actualModel = availabilityDAOImpl.convertToHibernateModel(availability);
        assertThat(expectedModel, is(actualModel));
    }

    @Test
    public void canSaveAvailability(){
        assertThat(availabilityDAOImpl.save(availability), is(1));
    }


    @Test
    public void doesNotSaveAvailabilityWithEmptyOwnerEmail(){
        availability = new Availability("", "ownerName", "title", new LocalDateTime(), new LocalDateTime(), "status");
        assertThat(availabilityDAOImpl.save(availability), is(FAILED_TO_CREATE));
    }

    @Test
    public void doesNotSaveAvailabilityWithEmptyOwnerName(){
        availability = new Availability("ownerEmail", "", "title", new LocalDateTime(), new LocalDateTime(), "status");
        assertThat(availabilityDAOImpl.save(availability), is(FAILED_TO_CREATE));
    }

    @Test
    public void doesNotSaveAvailabilityWithEmptyTitle(){
        availability = new Availability("ownerEmail", "ownerName", "", new LocalDateTime(), new LocalDateTime(), "status");
        assertThat(availabilityDAOImpl.save(availability), is(FAILED_TO_CREATE));
    }

    @Test
    public void doesNotSaveAvailabilityWithEmptyStatus(){
        availability = new Availability("ownerEmail", "ownerName", "title", new LocalDateTime(), new LocalDateTime(), "");
        assertThat(availabilityDAOImpl.save(availability), is(FAILED_TO_CREATE));
    }

    @Test
    public void doesNotSaveAvailabilityWithEmptyStartDate(){
        availability = new Availability("ownerEmail", "ownerName", "title", null, new LocalDateTime(), "status");
        assertThat(availabilityDAOImpl.save(availability), is(FAILED_TO_CREATE));
    }

    @Test
    public void doesNotSaveAvailabilityWithEmptyEndDate(){
        availability = new Availability("ownerEmail", "ownerName", "title", new LocalDateTime(), null, "status");
        assertThat(availabilityDAOImpl.save(availability), is(FAILED_TO_CREATE));
    }


}

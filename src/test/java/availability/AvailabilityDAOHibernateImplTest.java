package availability;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
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

    AvailabilityDAOHibernateImpl availabilityDAOImpl;
    Availability availability;
    Session mockSession;
    SessionFactory mockSessionFactory;

    @Before
    public void setup(){
        availabilityDAOImpl = new AvailabilityDAOHibernateImpl();
        availability = new Availability("ownerEmail", "ownerName", "title", new DateTime(), new DateTime(), "status");
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

//    @Test
//    public void canSaveAvailability(){
//        assertThat(availabilityDAOImpl.create(availability), is(1));
//    }


//    @Test
//    public void doesNotSaveAvailabilityWithEmptyOwnerEmail(){
//        availability = new Availability("", "ownerName", "title", new DateTime(), new DateTime(), "status");
//        assertThat(availabilityDAOImpl.create(availability), is(-1));
//    }


}

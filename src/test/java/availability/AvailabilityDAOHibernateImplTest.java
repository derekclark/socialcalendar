package availability;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.AvailabilityDAOHibernateImpl;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
    }

    @Test
    public void canCreateInstance(){
        assertTrue(availabilityDAOImpl instanceof AvailabilityDAOHibernateImpl);
    }

    @Test
    public void canSaveAvailability(){
        when(mockSession.save(anyObject())).thenReturn(1);
        availabilityDAOImpl.create(availability);
        verify(mockSession).save(anyObject());
    }
}

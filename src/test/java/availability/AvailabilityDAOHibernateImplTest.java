package availability;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.persistence.AvailabilityDAOHibernateImpl;

import static junit.framework.TestCase.assertTrue;

public class AvailabilityDAOHibernateImplTest {

    AvailabilityDAOHibernateImpl availabilityDAOImpl;

    @Before
    public void setup(){
        availabilityDAOImpl = new AvailabilityDAOHibernateImpl();
    }

    @Test
    public void canCreateInstance(){
        assertTrue(availabilityDAOImpl instanceof AvailabilityDAOHibernateImpl);
    }
}

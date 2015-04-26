package availability;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.Availability;
import uk.co.socialcalendar.availability.AvailabilityDAO;
import uk.co.socialcalendar.availability.InMemoryAvailability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InMemoryAvailabilityTest {
    AvailabilityDAO availabilityDAO;
    DateTime startDate, endDate;
    Availability availability1, availability2;

    @Before
    public void setup(){
        availabilityDAO = new InMemoryAvailability();
        startDate = new DateTime();
        endDate = new DateTime();
        availability1 = new Availability("ownerEmail","ownerName","title", startDate, endDate, "status");
        availability2 = new Availability("ownerEmail","ownerName","title", startDate, endDate, "status");
    }

    @Test
    public void canCreateInstance(){
        assertTrue(availabilityDAO instanceof AvailabilityDAO);
    }

    @Test
    public void canSaveOneAvailability(){
        int id = availabilityDAO.save(availability1);
        assertEquals(1, id);
    }

    @Test
    public void canReadOneAvailability(){
        int id = availabilityDAO.save(availability1);
        Availability actualAvailability = availabilityDAO.read(id);
        assertEquals(availability1, actualAvailability);

    }

    @Test
    public void CanSaveTwoAvailabilities(){
        int id1 = availabilityDAO.save(availability1);
        int id2 = availabilityDAO.save(availability2);
        assertEquals(1,id1);
        assertEquals(2,id2);
    }

    @Test
    public void CanReadTwoAvailabilities(){
        int id1 = availabilityDAO.save(availability1);
        int id2 = availabilityDAO.save(availability2);
        assertEquals(availability1, availabilityDAO.read(id1));
        assertEquals(availability2, availabilityDAO.read(id2));
    }

}

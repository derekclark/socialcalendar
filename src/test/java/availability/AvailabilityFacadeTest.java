package availability;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.AvailabilityDAO;
import uk.co.socialcalendar.availability.persistence.InMemoryAvailability;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacade;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacadeImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AvailabilityFacadeTest {
    AvailabilityFacadeImpl availabilityFacade;
    AvailabilityDAO availabilityDAO;
    Availability availability1, availability2;
    LocalDateTime startDate, endDate;
    @Before
    public void setup(){
        availabilityFacade = new AvailabilityFacadeImpl();
        availabilityDAO = new InMemoryAvailability();
        availabilityFacade.setAvailabilityDAO(availabilityDAO);
        startDate = new LocalDateTime();
        endDate = new LocalDateTime();
        availability1 = new Availability("ownerEmail","ownerName","title", startDate, endDate, "status");
        availability2 = new Availability("ownerEmail","ownerName","title", startDate, endDate, "status");
    }

    @Test
    public void canCreateInstance(){
        assertTrue(availabilityFacade instanceof AvailabilityFacade);
    }

    @Test
    public void canCreateAvailability(){
        assertEquals(1, availabilityFacade.create(availability1));
    }

    @Test
    public void canCreate2Availabilities(){
        assertEquals(1, availabilityFacade.create(availability1));
        assertEquals(2, availabilityFacade.create(availability2));
    }

    @Test
    public void canRead(){
        int id = availabilityFacade.create(availability1);
        availability1.setId(id);
        Availability actualAvailability = availabilityFacade.get(availability1.getId());
        System.out.println(actualAvailability.getId());
        assertEquals(availability1, actualAvailability);
    }

    @Test
    public void canUpdate(){
        availability1.setId(availabilityFacade.create(availability1));
        availability1.setTitle("updatedTitle");
        assertTrue(availabilityFacade.update(availability1));
        Availability actualAvailability = availabilityFacade.get(availability1.getId());
        assertEquals(availability1, actualAvailability);
    }
}

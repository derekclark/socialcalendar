package availability;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.AvailabilityDAO;
import uk.co.socialcalendar.availability.persistence.InMemoryAvailability;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InMemoryAvailabilityTest {
    public static final int NON_EXISTENT_ID = 9999;
    AvailabilityDAO availabilityDAO;
    LocalDateTime startDate, endDate;
    Availability availability1, availability2;

    @Before
    public void setup(){
        availabilityDAO = new InMemoryAvailability();
        startDate = new LocalDateTime();
        endDate = new LocalDateTime();
        availability1 = new Availability("ownerEmail1","ownerName1","title1", startDate, endDate, "status1");
        availability2 = new Availability("ownerEmail2","ownerName2","title2", startDate, endDate, "status2");
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
        availability1.setId(availabilityDAO.save(availability1));
        Availability actualAvailability = availabilityDAO.read(availability1.getId());
        assertEquals(availability1, actualAvailability);
    }

    @Test
    public void returnsNullIfIdDoesNotExist(){
        assertNull(availabilityDAO.read(NON_EXISTENT_ID));
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
        availability1.setId(availabilityDAO.save(availability1));
        availability2.setId(availabilityDAO.save(availability2));
        assertEquals(availability1, availabilityDAO.read(availability1.getId()));
        assertEquals(availability2, availabilityDAO.read(availability2.getId()));
    }

    @Test
    public void canUpdateAvailability(){
        availability1.setId(availabilityDAO.save(availability1));
        availability1.setTitle("changedTitle");
        assertTrue(availabilityDAO.update(availability1));
        Availability actualAvailability = availabilityDAO.read(availability1.getId());
        assertEquals("changedTitle", actualAvailability.getTitle());
    }

    @Test
    public void doesNotUpdateIfRecordDoesNotExist(){
        assertFalse(availabilityDAO.update(availability1));
    }
}

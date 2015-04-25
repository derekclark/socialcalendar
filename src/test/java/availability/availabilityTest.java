package availability;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.Availability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class availabilityTest {
    Availability availability;

    @Before
    public void setup(){
        availability = new Availability();
    }
    @Test
    public void canCreateInstance(){
        assertTrue(availability instanceof Availability);
    }

    @Test
    public void canSetTitle(){
        availability.setTitle("title");
        assertEquals("title",availability.getTitle());
    }

    @Test
    public void canSetStartDate(){
        DateTime dt = new DateTime();
        availability.setStartDate(dt);
        assertEquals(dt,availability.getStartDate());
    }

    @Test
    public void canSetEndDate(){
        DateTime dt = new DateTime();
        availability.setEndDate(dt);
        assertEquals(dt,availability.getEndDate());
    }

    @Test
    public void canSetOwnerEmail(){
        availability.setOwnerEmail("email");
        assertEquals("email", availability.getOwnerEmail());
    }

    @Test
    public void canSetOwnerName(){
        availability.setOwnerName("name");
        assertEquals("name",availability.getOwnerName());
    }

    @Test
    public void canSetStatus(){
        availability.setStatus("status");
        assertEquals("status",availability.getStatus());
    }

    @Test
    public void canSetId(){
        availability.setId(1);
        assertEquals(1,availability.getId());
    }
}

package availability;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.Availability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class availabilityTest {
    public static final String OWNER_EMAIL = "ownerEmail";
    public static final DateTime START_DATE = new DateTime(2015, 1, 2, 0, 0, 0);
    public static final DateTime END_DATE = new DateTime(2015, 1, 2, 1, 2, 30);
    public static final String OWNER_NAME = "ownerName";
    public static final String TITLE = "title";
    public static final String STATUS = "status";
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
        availability.setTitle(TITLE);
        assertEquals(TITLE,availability.getTitle());
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
        availability.setOwnerEmail(OWNER_EMAIL);
        assertEquals(OWNER_EMAIL, availability.getOwnerEmail());
    }

    @Test
    public void canSetOwnerName(){
        availability.setOwnerName(OWNER_NAME);
        assertEquals(OWNER_NAME,availability.getOwnerName());
    }

    @Test
    public void canSetStatus(){
        availability.setStatus(STATUS);
        assertEquals(STATUS,availability.getStatus());
    }

    @Test
    public void canSetId(){
        availability.setId(1);
        assertEquals(1,availability.getId());
    }

    @Test
    public void canCreateAvailabilityWithArgs(){
        availability = new Availability(OWNER_EMAIL, OWNER_NAME, TITLE,START_DATE, END_DATE, STATUS);
        assertEquals(OWNER_EMAIL,availability.getOwnerEmail());
        assertEquals(OWNER_NAME,availability.getOwnerName());
        assertEquals(TITLE,availability.getTitle());
        assertEquals(START_DATE,availability.getStartDate());
        assertEquals(END_DATE,availability.getEndDate());
        assertEquals(STATUS,availability.getStatus());
    }
}

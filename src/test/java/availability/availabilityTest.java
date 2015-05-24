package availability;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.entities.Availability;

import static org.junit.Assert.*;

public class AvailabilityTest {
    public static final String OWNER_EMAIL = "ownerEmail";
    public static final LocalDateTime START_DATE = new LocalDateTime(2015, 1, 2, 0, 0, 0);
    public static final LocalDateTime END_DATE = new LocalDateTime(2015, 1, 2, 1, 2, 30);
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
        LocalDateTime dt = new LocalDateTime();
        availability.setStartDate(dt);
        assertEquals(dt,availability.getStartDate());
    }

    @Test
    public void canSetEndDate(){
        LocalDateTime dt = new LocalDateTime();
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

    @Test
    public void testEquals(){
        Availability availability1 = new Availability(OWNER_EMAIL, OWNER_NAME, TITLE,START_DATE, END_DATE, STATUS);
        Availability availability2 = new Availability(OWNER_EMAIL, OWNER_NAME, TITLE,START_DATE, END_DATE, STATUS);
        assertEquals(availability1, availability2);
    }

    @Test
    public void testNotEqualsOwnerEmail(){
        Availability availability1 = new Availability(OWNER_EMAIL, OWNER_NAME, TITLE,START_DATE, END_DATE, STATUS);
        Availability availability2 = new Availability("differentEmail", OWNER_NAME, TITLE,START_DATE, END_DATE, STATUS);
        assertNotEquals(availability1, availability2);
    }

    @Test
    public void testNotEqualsOwnerName(){
        Availability availability1 = new Availability(OWNER_EMAIL, OWNER_NAME, TITLE,START_DATE, END_DATE, STATUS);
        Availability availability2 = new Availability(OWNER_EMAIL, "different", TITLE,START_DATE, END_DATE, STATUS);
        assertNotEquals(availability1, availability2);
    }

    @Test
    public void testNotEqualsTitle(){
        Availability availability1 = new Availability(OWNER_EMAIL, OWNER_NAME, TITLE,START_DATE, END_DATE, STATUS);
        Availability availability2 = new Availability(OWNER_EMAIL, OWNER_NAME, "different",START_DATE, END_DATE, STATUS);
        assertNotEquals(availability1, availability2);
    }

    @Test
    public void testNotEqualsStartDate(){
        LocalDateTime differentDateTime = new LocalDateTime();
        Availability availability1 = new Availability(OWNER_EMAIL, OWNER_NAME, TITLE,START_DATE, END_DATE, STATUS);
        Availability availability2 = new Availability(OWNER_EMAIL, OWNER_NAME, TITLE,differentDateTime, END_DATE, STATUS);
        assertNotEquals(availability1, availability2);
    }

    @Test
    public void testNotEqualsEndDate(){
        LocalDateTime differentDateTime = new LocalDateTime();
        Availability availability1 = new Availability(OWNER_EMAIL, OWNER_NAME, TITLE,START_DATE, END_DATE, STATUS);
        Availability availability2 = new Availability(OWNER_EMAIL, OWNER_NAME, TITLE,START_DATE, differentDateTime, STATUS);
        assertNotEquals(availability1, availability2);
    }
    @Test
    public void testNotEqualsStatus(){
        Availability availability1 = new Availability(OWNER_EMAIL, OWNER_NAME, TITLE,START_DATE, END_DATE, STATUS);
        Availability availability2 = new Availability(OWNER_EMAIL, OWNER_NAME, TITLE,START_DATE, END_DATE, "different");
        assertNotEquals(availability1, availability2);
    }





}

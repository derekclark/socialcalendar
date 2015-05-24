package availability;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.AvailabilityHibernateModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AvailabilityHibernateModelTest {

    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String TITLE = "title";
    public static final String STATUS = "status";
    public static final LocalDateTime START_DATE = new LocalDateTime();
    public static final LocalDateTime END_DATE = new LocalDateTime();
    AvailabilityHibernateModel model;
    Availability availability;

    @Before
    public void setup(){
        model=new AvailabilityHibernateModel();
        availability = new Availability(EMAIL, NAME, TITLE, START_DATE, END_DATE, STATUS);
    }

    @Test
    public void canCreateInstance(){
        assertTrue(model instanceof AvailabilityHibernateModel);
    }

    @Test
    public void canCreateModelFromAvailability(){
        model = new AvailabilityHibernateModel(availability);
        assertEquals(EMAIL, model.getOwnerEmail());
        assertEquals(NAME, model.getOwnerName());
        assertEquals(TITLE, model.getTitle());
        assertEquals(START_DATE, model.getStartDate());
        assertEquals(END_DATE, model.getEndDate());
        assertEquals(STATUS, model.getStatus());
    }

}

package availability;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.AvailabilityHibernateModel;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserHibernateModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
    Set<User> sharedList;

    @Before
    public void setup(){
    }

    @Test
    public void canCreateModelFromAvailability(){
        Availability availability = createAvailabilityWith2SharedUsers();
        model = new AvailabilityHibernateModel(availability);
        assertEquals(EMAIL, model.getOwnerEmail());
        assertEquals(NAME, model.getOwnerName());
        assertEquals(TITLE, model.getTitle());
        assertEquals(START_DATE, model.getStartDate());
        assertEquals(END_DATE, model.getEndDate());
        assertEquals(STATUS, model.getStatus());

        UserHibernateModel expectedUser1=new UserHibernateModel(new User("EMAIL1", "NAME1", "FACEBOOK1"));
        UserHibernateModel expectedUser2=new UserHibernateModel(new User("EMAIL2", "NAME2", "FACEBOOK2"));

        assertTrue(findElementInSet(model.getSharedList(), expectedUser1));
        assertTrue(findElementInSet(model.getSharedList(), expectedUser2));
    }

    @Test
    public void testEquality(){
        Availability availability1 = createAvailabilityWith2SharedUsers();
        Availability availability2 = createAvailabilityWith2SharedUsers();
        AvailabilityHibernateModel availabilityHibernateModel1 = new AvailabilityHibernateModel(availability1);
        AvailabilityHibernateModel availabilityHibernateModel2 = new AvailabilityHibernateModel(availability2);
        assertEquals(availabilityHibernateModel2, availabilityHibernateModel2);
    }

    @Test
    public void testInEquality(){
        Availability availability1 = createAvailabilityWith2SharedUsers();
        Availability availability2 = createAvailabilityWithNoSharedUsers();
        AvailabilityHibernateModel availabilityHibernateModel1 = new AvailabilityHibernateModel(availability1);
        AvailabilityHibernateModel availabilityHibernateModel2 = new AvailabilityHibernateModel(availability2);
        assertEquals(availabilityHibernateModel2, availabilityHibernateModel2);
    }

    public boolean findElementInSet(Set<UserHibernateModel> set, UserHibernateModel userHibernateModel){
        Iterator<UserHibernateModel> iterator = set.iterator();
        while(iterator.hasNext()) {
            UserHibernateModel setElement = iterator.next();
            if(setElement.equals(userHibernateModel)) {
                return true;
            }
        }
        return false;
    }


    public Availability createAvailabilityWith2SharedUsers(){
        model=new AvailabilityHibernateModel();
        sharedList = new HashSet<User>();
        sharedList.add(new User("EMAIL1", "NAME1", "FACEBOOK1"));
        sharedList.add(new User("EMAIL2", "NAME2", "FACEBOOK2"));
        Availability availability = new Availability(EMAIL, NAME, TITLE, START_DATE, END_DATE, STATUS, sharedList);
        availability.setId(1);
        return availability;
    }

    public Availability createAvailabilityWithNoSharedUsers(){
        model=new AvailabilityHibernateModel();
        sharedList = new HashSet<User>();
        Availability availability = new Availability(EMAIL, NAME, TITLE, START_DATE, END_DATE, STATUS, sharedList);
        availability.setId(1);
        return availability;
    }

}

package availability;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.AvailabilityDAO;
import uk.co.socialcalendar.availability.persistence.InMemoryAvailability;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacadeImpl;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserDAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AvailabilityFacadeTest {
    AvailabilityFacadeImpl availabilityFacade;
    AvailabilityDAO availabilityDAO;
    Availability availability1, availability2;
    LocalDateTime startDate, endDate;
    UserDAO mockUserDAO;

    @Before
    public void setup(){
        availabilityFacade = new AvailabilityFacadeImpl();
        availabilityDAO = new InMemoryAvailability();
        availabilityFacade.setAvailabilityDAO(availabilityDAO);
        startDate = new LocalDateTime();
        endDate = new LocalDateTime();
        availability1 = new Availability("ownerEmail","ownerName","title", startDate, endDate, "status");
        availability2 = new Availability("ownerEmail","ownerName","title", startDate, endDate, "status");
        mockUserDAO = mock(UserDAO.class);
        availabilityFacade.setUserDAO(mockUserDAO);
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
        createAvailabilityInMemory();
        Availability actualAvailability = availabilityFacade.get(availability1.getId());
        System.out.println(actualAvailability.getId());
        assertEquals(availability1, actualAvailability);
    }

    @Test
    public void canUpdate(){
        createAvailabilityInMemory();
        availability1.setTitle("updatedTitle");
        assertTrue(availabilityFacade.update(availability1));
        Availability actualAvailability = availabilityFacade.get(availability1.getId());
        assertEquals(availability1, actualAvailability);
    }

    @Test
    public void getUserListFromSelectedList(){
        List<String> selectedList = getSelectedUserList();
        Set<User> expectedUserList = mockExpectedUsers();
        Set <User> actualUserList = availabilityFacade.getUserList(selectedList);
        assertEquals(expectedUserList, actualUserList);
    }

    @Test
    public void sharesAvailabilityWithSelectedUsers(){
        List<String> selectedList = getSelectedUserList();
        Set<User> expectedUserList = mockExpectedUsers();

        createAvailabilityInMemory();

        availabilityFacade.shareWithUsers(availability1, selectedList);

//        Availability savedAvailability = availabilityFacade.get(1);
        assertEquals(expectedUserList, availability1.getSharedList());
        assertEquals(2,availability1.getSharedList().size());
    }

    public void createAvailabilityInMemory(){
        int id = availabilityFacade.create(availability1);
        availability1.setId(id);
    }

    public Set<User> mockExpectedUsers(){
        User user1=new User("EMAIL1", "NAME1", "FACEBOOK1");
        User user2=new User("EMAIL1", "NAME1", "FACEBOOK1");
        Set<User> expectedUserList = new HashSet<User>();
        expectedUserList.add(user1);
        expectedUserList.add(user2);
        when(mockUserDAO.read("EMAIL1")).thenReturn(user1);
        when(mockUserDAO.read("EMAIL2")).thenReturn(user2);

        return expectedUserList;
    }

    public List<String> getSelectedUserList(){
        List<String> selectedList = new ArrayList<String>();
        selectedList.add("EMAIL1");
        selectedList.add("EMAIL2");
        return selectedList;
    }

}

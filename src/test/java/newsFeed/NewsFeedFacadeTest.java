package newsFeed;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.AvailabilityDAO;
import uk.co.socialcalendar.newsFeed.NewsFeedFacadeImpl;
import uk.co.socialcalendar.newsFeed.NewsFeedLine;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewsFeedFacadeTest {
    public static final String ME = "me";
    NewsFeedFacadeImpl newsFeedFacade;
    AvailabilityDAO mockAvailabilityDAO;
    private static final LocalDateTime START_DATE = new LocalDateTime(2015, 1, 2, 0, 0, 0);
    private static final LocalDateTime END_DATE = new LocalDateTime(2015, 1, 2, 0, 0, 30);

    @Before
    public void setup(){
        newsFeedFacade = new NewsFeedFacadeImpl();
        mockAvailabilityDAO = mock(AvailabilityDAO.class);
        newsFeedFacade.setAvailabilityDAO(mockAvailabilityDAO);
    }

    @Test
    public void returnsNoAvailabilitiesIfIHaveNone(){
        List<Availability> expectedAvailabilities = new ArrayList<Availability>();
        assertEquals(expectedAvailabilities, newsFeedFacade.getMyAvailabilities(ME));
    }

    @Test
    public void getsMyNewsFeedWhenIOnlyHaveMyAvailabilities(){
        List<Availability> expectedAvailabilities = setMyAvailabilities();
        when(mockAvailabilityDAO.readAllOwnersOpenAvailabilities(ME)).thenReturn(expectedAvailabilities);
        List<NewsFeedLine> expectedNewsFeedLineList = setExpectedNewsFeedLineList();
        assertEquals(expectedNewsFeedLineList, newsFeedFacade.getNewsFeed(ME));
    }

    @Test
    public void setsLineUrl(){
        List<Availability> expectedAvailabilities = setMyAvailabilities();
        when(mockAvailabilityDAO.readAllOwnersOpenAvailabilities(ME)).thenReturn(expectedAvailabilities);
        assertEquals("amendAvailability?availabilityId=1", newsFeedFacade.getNewsFeed(ME).get(0).getUrl());
        assertEquals("amendAvailability?availabilityId=2", newsFeedFacade.getNewsFeed(ME).get(1).getUrl());
    }

    public List<Availability> setMyAvailabilities(){
        List<Availability> expectedAvailabilities = new ArrayList<Availability>();
        Availability availability1 = new Availability(ME, "myName","title",START_DATE, END_DATE,"status");
        availability1.setId(1);

        Availability availability2 = new Availability(ME, "myName2","title2",START_DATE, END_DATE,"status");
        availability2.setId(2);

        expectedAvailabilities.add(availability1);
        expectedAvailabilities.add(availability2);
        return expectedAvailabilities;
    }
    public List<NewsFeedLine> setExpectedNewsFeedLineList(){
        List<NewsFeedLine> expectedNewsFeedLineList = new ArrayList<NewsFeedLine>();
        NewsFeedLine newsFeedLine1 = new NewsFeedLine(1, "title", START_DATE, END_DATE, "", "", "myName", ME,
                "", "", "","",null, null, null, null, "", "", false);
        NewsFeedLine newsFeedLine2 = new NewsFeedLine(2, "title2", START_DATE, END_DATE, "", "", "myName2", ME,
                "", "", "","",null, null, null, null, "", "", false);

        expectedNewsFeedLineList.add(newsFeedLine1);
        expectedNewsFeedLineList.add(newsFeedLine2);
        return expectedNewsFeedLineList;
    }

}

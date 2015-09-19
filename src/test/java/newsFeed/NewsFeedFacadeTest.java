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
    private static final LocalDateTime START_DATE = new LocalDateTime(2015, 1, 2, 14, 27, 0);
    private static final LocalDateTime END_DATE = new LocalDateTime(2015, 1, 2, 15, 45, 30);
    static final String PRETTY_DATE_PATTERN = "YYYY-MM-dd HH:mm";

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
        assertEquals("amendAvailability?id=1", newsFeedFacade.getNewsFeed(ME).get(0).getUrl());
        assertEquals("amendAvailability?id=2", newsFeedFacade.getNewsFeed(ME).get(1).getUrl());
    }

    @Test
    public void convertsDateTimeToPrettyDates(){
        List<Availability> expectedAvailabilities = setMyAvailabilities();
        when(mockAvailabilityDAO.readAllOwnersOpenAvailabilities(ME)).thenReturn(expectedAvailabilities);
        assertEquals("2015-01-02 14:27", newsFeedFacade.getNewsFeed(ME).get(0).getStartDateTime());
        assertEquals("2015-01-02 15:45", newsFeedFacade.getNewsFeed(ME).get(0).getEndDateTime());
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
        NewsFeedLine newsFeedLine1 = new NewsFeedLine(1, "title",
                START_DATE.toString(PRETTY_DATE_PATTERN), END_DATE.toString(PRETTY_DATE_PATTERN), "date line", "N",
                "myName", "me",
                "facebookid", "text line", "status","available",null, null, null, null, "url", "key", false);
        NewsFeedLine newsFeedLine2 = new NewsFeedLine(1, "title2",
                START_DATE.toString(PRETTY_DATE_PATTERN), END_DATE.toString(PRETTY_DATE_PATTERN), "date line", "N",
                "myName2", "me",
                "facebookid", "text line", "status","available",null, null, null, null, "url", "key", false);

        expectedNewsFeedLineList.add(newsFeedLine1);
        expectedNewsFeedLineList.add(newsFeedLine2);
        return expectedNewsFeedLineList;
    }

}

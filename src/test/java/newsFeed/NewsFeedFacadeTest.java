package newsFeed;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacade;
import uk.co.socialcalendar.newsFeed.NewsFeedFacadeImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewsFeedFacadeTest {
    NewsFeedFacadeImpl newsFeedFacade;

    AvailabilityFacade mockAvailabilityFacade;

    @Before
    public void setup(){
        newsFeedFacade = new NewsFeedFacadeImpl();
        mockAvailabilityFacade = mock(AvailabilityFacade.class);
        newsFeedFacade.setAvailabilityFacade(mockAvailabilityFacade);
    }

    @Test
    public void returnsNoAvailabilitiesIfIHaveNone(){
        List<Availability> expectedAvailabilities = new ArrayList<Availability>();
        assertEquals(expectedAvailabilities, newsFeedFacade.getMyAvailabilities("me"));
    }

    @Test
    public void returnsMyAvailabilities(){
        List<Availability> expectedAvailabilities = new ArrayList<Availability>();
        expectedAvailabilities.add(new Availability("me", "myName","title",new LocalDateTime(), new LocalDateTime(),"status"));
        when(mockAvailabilityFacade.getOwnersOpenAvailabilities("me")).thenReturn(expectedAvailabilities);
        assertEquals(expectedAvailabilities, newsFeedFacade.getMyAvailabilities("me"));
    }
}

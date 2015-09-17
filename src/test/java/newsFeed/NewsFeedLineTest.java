package newsFeed;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import uk.co.socialcalendar.newsFeed.NewsFeedLine;

import static org.junit.Assert.assertEquals;

public class NewsFeedLineTest {

    NewsFeedLine newsFeedLine = new NewsFeedLine();
    public static final LocalDateTime START_DATE = new LocalDateTime(2015, 1, 2, 0, 0, 0);

    @Test
    public void canSetAvailabilityId(){
        newsFeedLine.setAvailabilityId(1);
        assertEquals(1, newsFeedLine.getAvailabilityId());
    }

    @Test
    public void canSetStartDate(){
        newsFeedLine.setStartDateTime(START_DATE);
        assertEquals(START_DATE, newsFeedLine.getStartDateTime());
    }
}
